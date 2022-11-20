#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>
#include <time.h>
#include <pthread.h>
#include <unistd.h>
#include "server.h"


// Structs
struct Npc {
    char name[3];
    float xPos;
    float yPos;
    float direction;
    char moves;
    char floor;
    
};

struct Player {
    char name[3];
    float xPos;
    float yPos;
    char floor;
    char level;
    char lifes;
    int points;
    
};

char blockWidth = 40;

// Global constants
#define PI 3.14159265359

// Global variables
bool isStart = true;

char numFloorsChanged = 0;

char floors[5][24];

struct Player sPlayers[2]; // Maximo 2 jugadores
char numPlayers = 0;

struct Npc sNPCs[20]; // Maximo 20 Npcs
char numNPCs = 0;

time_t t;

pthread_t thread_id;

// Functions
void createFloors(void) {
    for (char i = 0; i < 5; i++) {
        for (char j = 0; j < 24; j++) {
            floors[i][j] = 'F';
        }
    }
}

void createNPCs(char name[3], float xPos, float yPos, float direction, char moves) {
    strcpy(sNPCs[numNPCs].name, name);
    sNPCs[numNPCs].xPos = xPos;
    sNPCs[numNPCs].yPos = yPos;
    sNPCs[numNPCs].floor = (5 - (yPos - 3.0)/5.0);
    sNPCs[numNPCs].direction = direction;
    sNPCs[numNPCs].moves = moves;
    numNPCs++;
    
    
}

void createPlayers(char name[3], float xPos, float yPos) {
    strcpy(sPlayers[numPlayers].name, name);
    sPlayers[numPlayers].xPos = xPos;
    sPlayers[numPlayers].yPos = yPos;
    sPlayers[numPlayers].lifes = 3;
    sPlayers[numPlayers].level = 1;
    sPlayers[numPlayers].points = 0;
    numPlayers++;
}

void *createNPCsInExecution(void *vargp){
    
    char name[3];
    int floor;
    
    while(true) {
        printf("\nNew NPC:");
        
        printf("\nID:");
        scanf("%s", name);
        
        printf("\nFloor:");
        scanf("%i", &floor);
        
        switch (name[0]) {
            case 'S':
                createNPCs(name, rand() % 24, (5-floor)*5, 3*PI/2, 4);
                break;
            case 'B':
                createNPCs(name, rand() % 24, (5-floor)*5, PI/2, 1);
                break;
            case 'P':
                createNPCs(name, rand() % 24, (5-floor)*5 - 1, 3*PI/2, 1);
                break;
                
            default:
                createNPCs(name, rand() % 24, (5-floor)*5 + 2, 0, 1);
                
                break;
        }
    }
    return NULL;
}

void createGame(char nPlyrs) {
    // Floors
    createFloors();
    
    // NPCs
    createNPCs("S1", 1, 17, 0, -1);
    createNPCs("S2", 22, 12, PI, -1);
    createNPCs("S3", 1, 7, 0, -1);
    createNPCs("S4", 22, 2, PI, -1);

    createNPCs("B1", 22, 0, 3*PI/4, 1);
    createNPCs("B2", 1, 0, PI/4, 1);

    createNPCs("P1", 16, 4, 3*PI/2, 1);

    createNPCs("A1", 22, 17, 0, 1);
    createNPCs("B1", 1, 12, 0, 1);
    createNPCs("O1", 22, 7, 0, 1);
    createNPCs("L1", 1, 2, 0, 1);

    // Players
    switch (nPlyrs) {
        case 2:
            createPlayers("P1", 1, 21);
            createPlayers("P2", 22, 21);
            break;
            
        default:
            createPlayers("P1", 1, 21);
            break;
    }
    
    srand((unsigned) time(&t));
    
    pthread_create(&thread_id, NULL, createNPCsInExecution, NULL);
//    pthread_join(thread_id, NULL);
}

void updateNPC(float x, float y, float direction, char moves, char i){
    sNPCs[i].xPos = x;
    sNPCs[i].yPos = y;
    sNPCs[i].floor = (5 - (y - 3.0)/5.0);
    sNPCs[i].direction = direction;
    sNPCs[i].moves = moves;
}

void updatePlayer(float x, float y, char level, char lifes, int points, char i){
    sPlayers[i].xPos = x;
    sPlayers[i].yPos = y;
    sPlayers[i].floor = (5 - (y - 3.0)/5.0);
    sPlayers[i].level = level;
    sPlayers[i].lifes = lifes;
    sPlayers[i].points = points;
}

void movePlayers(float positionList[2][2]){
    for (char i = 0; i < numPlayers; i++) {
        updatePlayer(positionList[i][0], positionList[i][1], sPlayers[i].level, sPlayers[i].lifes, sPlayers[i].points,  i);
    }
}

void moveNPCs(void){
    for (char i = 0; i < numNPCs; i++) {

        // move
        updateNPC(sNPCs[i].xPos + 0.5 * cosf(sNPCs[i].direction), sNPCs[i].yPos - 0.5 * sinf(sNPCs[i].direction), sNPCs[i].direction, sNPCs[i].moves, i);
        
        // Hit
        for (char j = 0; j < numPlayers; j++) {
            
            if (sPlayers[j].floor == sNPCs[i].floor){ // Same floor
                if( ( ((sPlayers[j].xPos + 1) > (sNPCs[i].xPos)) && ((sPlayers[j].xPos) < (sNPCs[i].xPos + 1)) ) &&
                   ( ((sPlayers[j].yPos + 2) > (sNPCs[i].yPos)) && ((sPlayers[j].yPos) < (sNPCs[i].yPos +  1)) ) ) { // Hitbox
                    if (sNPCs[i].name[0] == 'S' || sNPCs[i].name[0] == 'B'){
                        updatePlayer(sPlayers[j].xPos, sPlayers[j].yPos, sPlayers[j].level, sPlayers[j].lifes - 1, sPlayers[j].points, j);
                    }
                    else{
                        if(sNPCs[i].name[0] == 'O'){
                            updatePlayer(sPlayers[j].xPos, sPlayers[j].yPos, sPlayers[j].level, sPlayers[j].lifes, sPlayers[j].points + 100, j);
                        }
                        else if(sNPCs[i].name[0] == 'A'){
                            updatePlayer(sPlayers[j].xPos, sPlayers[j].yPos, sPlayers[j].level, sPlayers[j].lifes, sPlayers[j].points + 200, j);
                        }
                        else if(sNPCs[i].name[0] == 'E'){
                            updatePlayer(sPlayers[j].xPos, sPlayers[j].yPos, sPlayers[j].level, sPlayers[j].lifes, sPlayers[j].points + 300, j);
                        }
                        else {
                            updatePlayer(sPlayers[j].xPos, sPlayers[j].yPos, sPlayers[j].level, sPlayers[j].lifes, sPlayers[j].points + 400, j);
                        }
                        updateNPC(sNPCs[i].xPos, sNPCs[i].yPos + 15, sNPCs[i].direction, -1, i);
                    }
                }
            }
        }
        
        // Out of map in x
        if(sNPCs[i].xPos >= 24 || sNPCs[i].xPos <= -1){
            updateNPC(23 - sNPCs[i].xPos, sNPCs[i].yPos, sNPCs[i].direction, sNPCs[i].moves, i);
        }

        // NPCs differences
        
        if(sNPCs[i].name[0] == 'S'){ // Seals
            
            if (sNPCs[i].moves > 0){
                updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, sNPCs[i].direction, sNPCs[i].moves - 1, i);
                if(sNPCs[i].moves == 0){
                    updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, 0, -1, i);
                }
            }
            
            else{
                // Encima de un hueco
                char iFloor = sNPCs[i].floor - 1;
                char jBlock = truncf(sNPCs[i].xPos);
                if(floors[iFloor][jBlock] == 'H'){
                    updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, 3*PI/2, 10, i);
                }
                else{
                    // Contacto a los lados con un hueco (funciona para cualquier direccion)
                    jBlock = jBlock + cosf(sNPCs[i].direction);
                    if (floors[iFloor][jBlock] == 'H') {
                        updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, PI - sNPCs[i].direction, sNPCs[i].moves, i);
                    }
                }
                
            }
        }
        
        else if(sNPCs[i].name[0] == 'B'){// Birds
            if (sNPCs[i].moves > 0){
                updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, sNPCs[i].direction, sNPCs[i].moves - 1, i);
                
                if(sNPCs[i].moves == 0){
                    if(sNPCs[i].direction > PI){
                        updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, 2 * PI - sNPCs[i].direction, 32, i);
                    }
                    else{
                        float xDiff = sPlayers[0].xPos - sNPCs[i].xPos;
                        float yDiff = sPlayers[0].yPos - sNPCs[i].yPos;
                        
                        float direction = acosf(xDiff/(sqrtf(powf(xDiff, 2) + powf(yDiff, 2))));
                        
                        if(yDiff > 0) direction = direction * (-1) + 2*PI;
                        updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, direction, 28, i);
                    }
                }
            }
        }
        
        else if(sNPCs[i].name[0] == 'P'){ // Peaks
            if (sNPCs[i].moves > 0){
                updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, sNPCs[i].direction, sNPCs[i].moves - 1, i);

                if(sNPCs[i].moves == 0){
                    char xPosRandom = rand() % 24;
                    char yPosRandom = (rand() % 4) * 5 + 4;
                    updateNPC(xPosRandom, yPosRandom, (3 * PI) / 2, 40, i);

                }
            }
        }
        
        else if(sNPCs[i].name[0] == 'A' || sNPCs[i].name[0] == 'E' || sNPCs[i].name[0] == 'O'|| sNPCs[i].name[0] == 'L'){ // Bananas, Eggplant, Orange, Lettuce
            if (sNPCs[i].moves > 0){
                updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, sNPCs[i].direction, sNPCs[i].moves - 1, i);

                if(sNPCs[i].moves == 0){
                    updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, PI - sNPCs[i].direction, 1, i);

                }
            }
        }
    }
}

char* answerUpdate(void) {
    char response[1000] = "{ \"evento\" : \"update\", \"jugadores\" : [ ";
    for (char i = 0; i < numPlayers; i++) {
        strcat(response, "{ \"id\" : \"");
        strcat(response, sPlayers[i].name);
        strcat(response, "\", ");

        strcat(response, "\"x\" : ");
        char *x;
        asprintf(&x, "%d", (short) (sPlayers[i].xPos * blockWidth));
        strcat(response, x);
        free(x);
        strcat(response, ", ");
        
        strcat(response, "\"y\" : ");
        char *y;
        asprintf(&y, "%d", (short) (sPlayers[i].yPos * blockWidth));
        strcat(response, y);
        free(y);
        strcat(response, ", ");
        
        strcat(response, "\"level\" : ");
        char *level;
        asprintf(&level, "%d", (short) (sPlayers[i].level));
        strcat(response, level);
        free(level);
        strcat(response, ", ");
        
        strcat(response, "\"lifes\" : ");
        char *lifes;
        asprintf(&lifes, "%d", (short) (sPlayers[i].lifes));
        strcat(response, lifes);
        free(lifes);
        strcat(response, ", ");
        
        strcat(response, "\"points\" : ");
        char *points;
        asprintf(&points, "%d", (short) (sPlayers[i].points));
        strcat(response, points);
        free(points);
        
        strcat(response, "}");
        
        if (i != numPlayers - 1) strcat(response, ", ");

    }
    
    strcat(response, "], \"npcs\" : [");

    for (char i = 0; i < numNPCs; i++) {
        strcat(response, "{ \"id\" : \"");
        strcat(response, sNPCs[i].name);
        strcat(response, "\", ");

        strcat(response, "\"x\" : ");
        char *x;
        asprintf(&x, "%d", (short) (sNPCs[i].xPos * blockWidth));
        strcat(response, x);
        free(x);
        strcat(response, ", ");
        
        strcat(response, "\"y\" : ");
        char *y;
        asprintf(&y, "%d", (short) (sNPCs[i].yPos * blockWidth));
        strcat(response, y);
        free(y);
        
        strcat(response, "}");
        
        if (i != numNPCs - 1) strcat(response, ", ");

    }
    
    strcat(response, "]}");
   
    char *pResponse = &response[0];
    
    return pResponse;
    
}

void updateGame(float positionList[2][2]){
    movePlayers(positionList);
    moveNPCs();
}

void sledgehammerEvent(char iPlayer){
    
    for (char i = 0; i < numNPCs; i++) {
        
        // Hit
            
        if (sPlayers[iPlayer].floor == sNPCs[i].floor){ // Same floor
            
            if( ( ((sPlayers[iPlayer].xPos + 2) > (sNPCs[i].xPos)) && ((sPlayers[iPlayer].xPos - 1) < (sNPCs[i].xPos + 1)) ) &&
               ( ((sPlayers[iPlayer].yPos + 2) > (sNPCs[i].yPos)) && ((sPlayers[iPlayer].yPos - 1) < (sNPCs[i].yPos +  1)) ) ) { // Hitbox
                
                updateNPC(sNPCs[i].xPos, sNPCs[i].yPos, PI - sNPCs[i].direction, sNPCs[i].moves, i);
                
                if (sNPCs[i].name[0] == 'S'){
                    updatePlayer(sPlayers[iPlayer].xPos, sPlayers[iPlayer].yPos, sPlayers[iPlayer].level, sPlayers[iPlayer].lifes, sPlayers[iPlayer].points + 400, iPlayer);
                }
                else if (sNPCs[i].name[0] == 'B'){
                    updatePlayer(sPlayers[iPlayer].xPos, sPlayers[iPlayer].yPos, sPlayers[iPlayer].level, sPlayers[iPlayer].lifes, sPlayers[iPlayer].points + 800, iPlayer);
                }
            }
        }
    }
}

void destroyBlockEvent(char iPlayer, char iFloor, char jBlock) {
    floors[iFloor][jBlock] = 'H';
    updatePlayer(sPlayers[iPlayer].xPos, sPlayers[iPlayer].yPos, sPlayers[iPlayer].level, sPlayers[iPlayer].lifes, sPlayers[iPlayer].points + 10, iPlayer);
}

void changeFloorsEvent(void){
    numFloorsChanged++;
    
    // Floors added
    for (char i = 0; i < 4; i++) {
        if(i < 2){
            for (char j = 0; j < 24; j++) {
                floors[i][j] = floors[i+3][j+3];
            }
        }
        else{
            for (char j = 0; j < 24; j++) {
                floors[i][j] = 'F';
            }
        }
    }
    
    // NPCs positions
    for (char i = 0; i < numNPCs; i++) {
        
        if(sNPCs[i].floor > 0){ // In the game
            updateNPC(sNPCs[i].xPos, sNPCs[i].yPos + 15, sNPCs[i].direction, sNPCs[i].moves, i);
        }
            
        if(sNPCs[i].floor < 1){ // Out the game
            switch (sNPCs[i].name[0]) {
                case 'S':
                    updateNPC(rand() % 24, sNPCs[i].yPos - 25, rand() % 2 * PI, -1, i);
                    break;
                    
                case 'B':
                    updateNPC(rand() % 24, sNPCs[i].yPos - 25, PI/2, 1, i);
                    break;
                case 'P':
                    updateNPC(rand() % 24, (rand() % 4) * 5 + 4, 3*PI/2, 1, i);
                    break;
                default:
                    updateNPC(rand() % 24, sNPCs[i].yPos - 25, 0, 1, i);
                    break;
            }
        }
    }
    
    // Players lifes
    for (char i = 0; i < 0; i++) {
        if(sPlayers[i].floor < 4){
            updatePlayer(sPlayers[i].xPos, sPlayers[i].yPos, sPlayers[i].level, sPlayers[i].lifes - 1, sPlayers[i].points, i);
        }
        updatePlayer(sPlayers[i].xPos, sPlayers[i].yPos, sPlayers[i].level + 1, sPlayers[i].lifes, sPlayers[i].points, i);
    }
    
}

char* receiveMessage(struct messageBox mb){
    float positions[2][2] = {
        {(float) mb.player1.x / blockWidth, (float) mb.player1.y / blockWidth},
        {(float) mb.player2.x / blockWidth, (float) mb.player2.y / blockWidth}
    };
        
    switch (mb.event[0]) {
        case 'u':
            if(isStart){
                char nPlayers = 1;
                if(mb.player2.id != NULL) nPlayers = 2;
                createGame(nPlayers);
                isStart = false;
            }
            else{
                updateGame(positions);
            }
            break;
            
        case 's':
            sledgehammerEvent(atoi(&mb.player1.id[1]) - 1);
            break;
            
        case 'd':
            destroyBlockEvent(atoi(&mb.player1.id[1]) - 1, mb.player1.floorNumber - 1, mb.player1.blockNumber);
            break;
            
        case 'c':
            changeFloorsEvent();
            break;
    }
    
    return answerUpdate();
}
