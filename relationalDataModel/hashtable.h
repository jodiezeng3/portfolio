/*
 * hashtable.h
 *
 *  Created on: Nov 15, 2021
 *      Author: jzeng
 */

#ifndef HASHTABLE_H_
#define HASHTABLE_H_
#include "LinkedList.h"

typedef struct csg_tuple* csg_tupleList;
extern struct csg_tuple* new_csg_tuple(char course[5], int studentID, char grade[2]);
extern int csg_h(int x);
extern void csg_insert(char course[], int studentID, char grade[]);
extern LinkedList csg_lookup(char course[], char studentID[], char grade[]);
extern void csg_delete(char course[], char studentID[], char grade[]);
extern void csg_free();
extern void csg_print(struct csg_tuple* tuple);
extern void csg_print_list(LinkedList list);
extern void csg_print_all();

typedef struct snap_tuple* snap_tupleList;
extern struct snap_tuple* new_snap_tuple(int studentID, char name[], char address[], char phone[]);
extern int snap_h(int x);
extern void snap_insert(int studentID, char name[], char address[], char phone[]);
extern LinkedList snap_lookup(char studentID[], char name[], char address[], char phone[]);
extern void snap_delete(char studentID[], char name[], char address[], char phone[]);
extern void snap_free();
extern void snap_print(struct snap_tuple* tuple);
extern void snap_print_list(LinkedList list);
extern void snap_print_all();

typedef struct cp_tuple* cp_tupleList;
extern struct cp_tuple* new_cp_tuple(char course[], char prereq[]);
extern int cp_h(char x[]);
extern void cp_insert(char course[], char prereq[]);
extern LinkedList cp_lookup(char course[], char prereq[]);
extern void cp_delete(char course[], char prereq[]);
extern void cp_free();
extern void cp_print(struct cp_tuple*);
extern void cp_print_list(LinkedList list);
extern void cp_print_all();

typedef struct cdh_tuple* cdh_tupleList;
extern struct cdh_tuple* new_cdh_tuple(char* course, char* day, char* hour);
extern int cdh_h(char x[]);
extern void cdh_insert(char* course, char* day, char* hour);
extern LinkedList cdh_lookup(char* course, char* day, char* hour);
extern void cdh_delete(char course[], char day[], char hour[]);
extern void cdh_free();
extern void cdh_print(struct cdh_tuple*);
extern void cdh_print_list(LinkedList list);
extern void cdh_print_all();

typedef struct cr_tuple* cr_tupleList;
extern struct cr_tuple* new_cr_tuple(char* course, char* room);
extern int cr_h(char x[]);
extern void cr_insert(char* course, char* room);
extern LinkedList cr_lookup(char* course, char* room);
extern void cr_delete(char* course, char* room);
extern void cr_free();
extern void cr_print(struct cr_tuple*);
extern void cr_print_list(LinkedList list);
extern void cr_print_all();

extern void nameCourse_to_grade(char* name, char* course);
extern void nameHourDay_to_location(char* name, char* hour, char* day);

extern LinkedList csg_select_course(char* course);
extern void s_print_all();
extern void csg_projection_ID_select_course(char* course);
extern void crdh_print_all();
extern void cr_join_cdh_course();
extern void dh_print_all();
extern void cr_join_cdh_select_room_project_dayHour(char* room);

#endif /* HASHTABLE_H_ */
