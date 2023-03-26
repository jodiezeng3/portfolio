/*
 * hashtable.c
 *
 *  Created on: Nov 15, 2021
 *      Author: jzeng
 */
#include "hashtable.h"
#include "LinkedList.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * Course-StudentID-Grade Relation Hashtable
 */
typedef struct csg_tuple* csg_tupleList;
struct csg_tuple
{
	char course[5];
	int studentID;
	char grade[2];
};
static int csg_hashtable_SIZE = 1009;
LinkedList csg_hashtable[1009];

struct csg_tuple* new_csg_tuple(char course[], int studentID, char grade[])
{
	struct csg_tuple* this = (struct csg_tuple*) malloc(sizeof(struct csg_tuple));

	if(this == NULL)
		return NULL;

	strcpy(this->course, course);
	this->studentID = studentID;
	strcpy(this->grade, grade);

	return this;
}

int csg_h(int x)
{
	return x % 1009;
}

//new csg tuple (for insert)
void csg_insert(char course[], int studentID, char grade[])
{
	int key = csg_h(studentID);
	struct csg_tuple* tuple = new_csg_tuple(course, studentID, grade);

	if(csg_hashtable[key] == NULL)
	{
		csg_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(csg_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(csg_hashtable[key], tuple))
			LinkedList_add_at_end(csg_hashtable[key], tuple);
	}
}

LinkedList csg_lookup(char course[], char studentID[], char grade[])
{
	LinkedList set = new_LinkedList();

	if(strcmp(studentID, "*") != 0)
	{
		int idNum = atoi(studentID);
		int key = csg_h(idNum);
		LinkedListIterator list_iterator= LinkedList_iterator(csg_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);

			int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
			int grade_match = (strcmp(grade, "*") == 0)||(strcmp(tuple->grade, grade)==0);;

			if(course_match && grade_match)
			{
				LinkedList_add_at_end(set, tuple);
			}
		}

		free(list_iterator);
		return set;
	}

	for(int i = 0; i < csg_hashtable_SIZE; i++)
	{
		if(csg_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator= LinkedList_iterator(csg_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);

				int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
				int grade_match = (strcmp(grade, "*") == 0)||(strcmp(tuple->grade, grade)==0);;

				if(course_match && grade_match)
				{
					LinkedList_add_at_end(set, tuple);
				}
			}

			free(list_iterator);
		}
	}

	return set;
}

void csg_delete(char course[], char studentID[], char grade[]) //deletes ALL matching this pattern
{
	if(strcmp(studentID, "*") != 0)
		{
			int idNum = atoi(studentID);
			int key = csg_h(idNum);
			LinkedListIterator list_iterator= LinkedList_iterator(csg_hashtable[key]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);

				int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
				int grade_match = (strcmp(grade, "*") == 0)||(strcmp(tuple->grade, grade)==0);;

				if(course_match && grade_match)
				{
					LinkedList_remove(csg_hashtable[key], tuple);
				}
			}

			free(list_iterator);
			return;
		}

		for(int i = 0; i < csg_hashtable_SIZE; i++)
		{
			if(csg_hashtable[i] != NULL)
			{
				LinkedListIterator list_iterator= LinkedList_iterator(csg_hashtable[i]);
				while(LinkedListIterator_hasNext(list_iterator))
				{
					struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);

					int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
					int grade_match = (strcmp(grade, "*") == 0)||(strcmp(tuple->grade, grade)==0);;

					if(course_match && grade_match)
					{
						LinkedList_remove(csg_hashtable[i], tuple);
					}
				}

				free(list_iterator);
			}
		}
}

void csg_free()
{
	for(int i = 0; i < csg_hashtable_SIZE; i++)
	{
		if(csg_hashtable[i] != NULL)
			LinkedList_free(csg_hashtable[i], 1);
	}
}

void csg_print(struct csg_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("Course: %s  StudentID: %d  Grade: %s\n", tuple->course, tuple->studentID, tuple->grade);
}

void csg_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);
		csg_print(tuple);
	}
}

void csg_print_all()
{
	for(int i = 0; i < csg_hashtable_SIZE; i++)
	{
		if(csg_hashtable[i] != NULL)
			csg_print_list(csg_hashtable[i]);
	}
}

/*
 * StudentID-Name-Address-Phone Relation Hashtable
 */
typedef struct snap_tuple* snap_tupleList;
struct snap_tuple {
	int studentID;
	char name[30];
	char address[50];
	char phone[8];
};
static int snap_hashtable_SIZE = 1009;
LinkedList snap_hashtable[1009];

struct snap_tuple* new_snap_tuple(int studentID, char name[], char address[], char phone[])
{
	struct snap_tuple* this = (struct snap_tuple*)malloc(sizeof(struct snap_tuple));

	if(this == NULL)
		return NULL;

	this->studentID = studentID;
	strcpy(this->name, name);
	strcpy(this->address, address);
	strcpy(this->phone, phone);

	return this;
}

int snap_h(int x)
{
	return x % 1009;
}

void snap_insert(int studentID, char name[], char address[], char phone[])
{
	int key = snap_h(studentID);
	struct snap_tuple* tuple = new_snap_tuple(studentID, name, address, phone);

	if(snap_hashtable[key] == NULL)
	{
		snap_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(snap_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(snap_hashtable[key], tuple))
			LinkedList_add_at_end(snap_hashtable[key], tuple);
	}
}

LinkedList snap_lookup(char studentID[], char name[], char address[], char phone[])
{
	LinkedList set = new_LinkedList();

	if(strcmp(studentID, "*") != 0)
	{
		int idNum = atoi(studentID);
		int key = snap_h(idNum);
		LinkedListIterator list_iterator= LinkedList_iterator(snap_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct snap_tuple* tuple = LinkedListIterator_next(list_iterator);

			int name_match = (strcmp(name, "*") == 0)||(strcmp(tuple->name, name)==0);
			int address_match = (strcmp(address, "*") == 0)||(strcmp(tuple->address, address)==0);
			int phone_match = (strcmp(phone, "*") == 0)||(strcmp(tuple->phone, phone)==0);

			if(name_match && address_match && phone_match)
				LinkedList_add_at_end(set, tuple);
		}

		free(list_iterator);
		return set;
	}

	for(int i = 0; i < snap_hashtable_SIZE; i++)
	{
		if(snap_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator= LinkedList_iterator(snap_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct snap_tuple* tuple = LinkedListIterator_next(list_iterator);

				int name_match = (strcmp(name, "*") == 0)||(strcmp(tuple->name, name)==0);
				int address_match = (strcmp(address, "*") == 0)||(strcmp(tuple->address, address)==0);
				int phone_match = (strcmp(phone, "*") == 0)||(strcmp(tuple->phone, phone)==0);

				if(name_match && address_match && phone_match)
					LinkedList_add_at_end(set, tuple);
			}

			free(list_iterator);
		}
	}

	return set;
}

void snap_delete(char studentID[], char name[], char address[], char phone[])
{
	if(strcmp(studentID, "*") != 0)
		{
			int idNum = atoi(studentID);
			int key = snap_h(idNum);
			LinkedListIterator list_iterator= LinkedList_iterator(snap_hashtable[key]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct snap_tuple* tuple = LinkedListIterator_next(list_iterator);

				int name_match = (strcmp(name, "*") == 0)||(strcmp(tuple->name, name)==0);
				int address_match = (strcmp(address, "*") == 0)||(strcmp(tuple->address, address)==0);
				int phone_match = (strcmp(phone, "*") == 0)||(strcmp(tuple->phone, phone)==0);

				if(name_match && address_match && phone_match)
					LinkedList_remove(snap_hashtable[key], tuple);
			}

			free(list_iterator);
			return;
		}

		for(int i = 0; i < snap_hashtable_SIZE; i++)
		{
			if(snap_hashtable[i] != NULL)
			{
				LinkedListIterator list_iterator= LinkedList_iterator(snap_hashtable[i]);
				while(LinkedListIterator_hasNext(list_iterator))
				{
					struct snap_tuple* tuple = LinkedListIterator_next(list_iterator);

					int name_match = (strcmp(name, "*") == 0)||(strcmp(tuple->name, name)==0);
					int address_match = (strcmp(address, "*") == 0)||(strcmp(tuple->address, address)==0);
					int phone_match = (strcmp(phone, "*") == 0)||(strcmp(tuple->phone, phone)==0);

					if(name_match && address_match && phone_match)
						LinkedList_remove(snap_hashtable[i], tuple);
				}

				free(list_iterator);
			}
		}
}

void snap_free()
{
	for(int i = 0; i < snap_hashtable_SIZE; i++)
	{
		if(snap_hashtable[i] != NULL)
			LinkedList_free(snap_hashtable[i], 1);
	}
}

void snap_print(struct snap_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("StudentID: %d  Name: %s  Address: %s  Phone: %s\n", tuple->studentID, tuple->name, tuple->address, tuple->phone);
}

void snap_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct snap_tuple* tuple = LinkedListIterator_next(list_iterator);
		snap_print(tuple);
	}
}

void snap_print_all()
{
	for(int i = 0; i < snap_hashtable_SIZE; i++)
	{
		if(snap_hashtable[i] != NULL)
			snap_print_list(snap_hashtable[i]);
	}
}

/*
 * Course-Prereq Relation Hashtable
 */
typedef struct cp_tuple* cp_tupleList;
struct cp_tuple {
	char* course;
	char* prereq;
};
static int cp_hashtable_SIZE = 11;
LinkedList cp_hashtable[11];

struct cp_tuple* new_cp_tuple(char* course, char* prereq)
{
	struct cp_tuple* this = (struct cp_tuple*)malloc(sizeof(struct cp_tuple));

	if(this == NULL)
		return NULL;

	this->course = course;
	this->prereq = prereq;
	//FIXME for some reason, if i put strcpy prereq after course, it gets concat to the course variable, but if i put it before, there is nothing in prereq
	//functions for some weird reason still work with this bug. very confusing also that this didnt happen in prev relations

	return this;
}

/*
 * from FOCS textbook - sec 7.6 fig 7.11
 */
int cp_h(char x[])
{
	int sum = 0;
	for(int i = 0; x[i] != '\0'; i++)
		sum += x[i];

	return sum % cp_hashtable_SIZE;
}

void cp_insert(char course[], char prereq[])
{
	int key = cp_h(course);
	struct cp_tuple* tuple = new_cp_tuple(course, prereq);

	if(cp_hashtable[key] == NULL)
	{
		cp_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(cp_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(cp_hashtable[key], tuple))
			LinkedList_add_at_end(cp_hashtable[key], tuple);
	}
}

LinkedList cp_lookup(char course[], char prereq[])
{
	LinkedList set = new_LinkedList();

	if(strcmp(course, "*") != 0)
	{
		int key = cp_h(course);
		LinkedListIterator list_iterator = LinkedList_iterator(cp_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct cp_tuple* tuple = LinkedListIterator_next(list_iterator);

			int prereq_match = (strcmp(prereq, "*") == 0)||(strcmp(tuple->prereq, prereq)==0);
			int course_match = strcmp(tuple->course, course) == 0;

			if(prereq_match && course_match)
			{
				LinkedList_add_at_end(set, tuple);
			}
		}

		free(list_iterator);
		return set;
	}

	for(int i = 0; i < cp_hashtable_SIZE; i++)
	{
		if(cp_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(cp_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct cp_tuple* tuple = LinkedListIterator_next(list_iterator);

				int prereq_match = (strcmp(prereq, "*") == 0)||(strcmp(tuple->prereq, prereq)==0);

				if(prereq_match)
					LinkedList_add_at_end(set, tuple);
			}

			free(list_iterator);
		}
	}

	return set;
}

void cp_delete(char course[], char prereq[])
{
	if(strcmp(course, "*") != 0)
	{
		int key = cp_h(course);
		LinkedListIterator list_iterator = LinkedList_iterator(cp_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct cp_tuple* tuple = LinkedListIterator_next(list_iterator);

			int prereq_match = (strcmp(prereq, "*") == 0)||(strcmp(tuple->prereq, prereq)==0);
			int course_match = strcmp(tuple->course, course) == 0;

			if(prereq_match && course_match)
				LinkedList_remove(cp_hashtable[key], tuple);
		}

		free(list_iterator);
		return;
	}

	for(int i = 0; i < cp_hashtable_SIZE; i++)
	{
		if(cp_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(cp_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct cp_tuple* tuple = LinkedListIterator_next(list_iterator);

				int prereq_match = (strcmp(prereq, "*") == 0)||(strcmp(tuple->prereq, prereq)==0);

				if(prereq_match)
					LinkedList_remove(cp_hashtable[i], tuple);
			}

			free(list_iterator);
		}
	}
}

void cp_free()
{
	for(int i = 0; i < cp_hashtable_SIZE; i++)
	{
		if(cp_hashtable[i] != NULL)
			LinkedList_free(cp_hashtable[i], 1);
	}
}

void cp_print(struct cp_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("Course: %s  Prereq: %s\n", tuple->course, tuple->prereq);
}

void cp_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct cp_tuple* tuple = LinkedListIterator_next(list_iterator);
		cp_print(tuple);
	}
}

void cp_print_all()
{
	for(int i = 0; i < cp_hashtable_SIZE; i++)
	{
		if(cp_hashtable[i] != NULL)
			cp_print_list(cp_hashtable[i]);
	}
}

/*
 * Course-Day-Hour Relation Hashtable
 */
typedef struct cdh_tuple* cdh_tupleList;
struct cdh_tuple {
	char* course;
	char* day;
	char* hour;
};
static int cdh_hashtable_SIZE = 7;
LinkedList cdh_hashtable[7];

struct cdh_tuple* new_cdh_tuple(char* course, char* day, char* hour)
{
	struct cdh_tuple* this = (struct cdh_tuple*)malloc(sizeof(struct cdh_tuple));

	if(this == NULL)
		return this;

	//strcpy(this->course, course);
	this->course = course;
	this->day = day;
	this->hour = hour;

	return this;
}

int cdh_h(char x[])
{
	int sum = 0;
	for(int i = 0; x[i] != '\0'; i++)
		sum += x[i];

	return sum % cdh_hashtable_SIZE;
}

void cdh_insert(char* course, char* day, char* hour)
{
	int key = cdh_h(day);
	struct cdh_tuple* tuple = new_cdh_tuple(course, day, hour);

	if(cdh_hashtable[key] == NULL)
	{
		cdh_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(cdh_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(cdh_hashtable[key], tuple))
			LinkedList_add_at_end(cdh_hashtable[key], tuple);
	}
}

LinkedList cdh_lookup(char* course, char* day, char* hour)
{
	LinkedList set = new_LinkedList();

	if(strcmp(day, "*") != 0)
	{
		int key = cdh_h(day);
		LinkedListIterator list_iterator = LinkedList_iterator(cdh_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct cdh_tuple* tuple = LinkedListIterator_next(list_iterator);

			int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
			int hour_match = (strcmp(hour, "*") == 0)||(strcmp(tuple->hour, hour)==0);
			int day_match = strcmp(tuple->day, day) == 0;

			if(course_match && hour_match && day_match)
				LinkedList_add_at_end(set, tuple);
		}

		free(list_iterator);
		return set;
	}

	for(int i = 0; i < cdh_hashtable_SIZE; i++)
	{
		if(cdh_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(cdh_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct cdh_tuple* tuple = LinkedListIterator_next(list_iterator);

				int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
				int hour_match = (strcmp(hour, "*") == 0)||(strcmp(tuple->hour, hour)==0);

				if(course_match && hour_match)
					LinkedList_add_at_end(set, tuple);
			}

			free(list_iterator);
		}
	}

	return set;
}

void cdh_delete(char course[], char day[], char hour[])
{
	if(strcmp(day, "*") != 0)
	{
		int key = cdh_h(day);
		LinkedListIterator list_iterator = LinkedList_iterator(cdh_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct cdh_tuple* tuple = LinkedListIterator_next(list_iterator);

			int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
			int hour_match = (strcmp(hour, "*") == 0)||(strcmp(tuple->hour, hour)==0);
			int day_match = strcmp(tuple->day, day) == 0;

			if(course_match && hour_match && day_match)
				LinkedList_remove(cdh_hashtable[key], tuple);
		}

		free(list_iterator);
		return;
	}

	for(int i = 0; i < cdh_hashtable_SIZE; i++)
	{
		if(cdh_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(cdh_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct cdh_tuple* tuple = LinkedListIterator_next(list_iterator);

				int course_match = (strcmp(course, "*") == 0)||(strcmp(tuple->course, course)==0);
				int hour_match = (strcmp(hour, "*") == 0)||(strcmp(tuple->hour, hour)==0);

				if(course_match && hour_match)
					LinkedList_remove(cdh_hashtable[i], tuple);
			}

			free(list_iterator);
		}
	}
}

void cdh_free()
{
	for(int i = 0; i < cdh_hashtable_SIZE; i++)
	{
		if(cdh_hashtable[i] != NULL)
			LinkedList_free(cdh_hashtable[i], 1);
	}
}

void cdh_print(struct cdh_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("Course: %s  Day: %s  Hour: %s\n", tuple->course, tuple->day, tuple->hour);
}

void cdh_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct cdh_tuple* tuple = LinkedListIterator_next(list_iterator);
		cdh_print(tuple);
	}
}

void cdh_print_all()
{
	for(int i = 0; i < cdh_hashtable_SIZE; i++)
	{
		if(cdh_hashtable[i] != NULL)
			cdh_print_list(cdh_hashtable[i]);
	}
}

/*
 * Course-Room Relation Hashtable
 */
typedef struct cr_tuple* cr_tupleList;
struct cr_tuple {
	char* course;
	char* room;
};
static int cr_hashtable_SIZE = 3;
LinkedList cr_hashtable[3];

struct cr_tuple* new_cr_tuple(char* course, char* room)
{
	struct cr_tuple* this = (struct cr_tuple*)malloc(sizeof(struct cr_tuple));

	if(this == NULL)
		return this;

	this->course = course;
	this->room = room;

	return this;
}

int cr_h(char x[])
{
	int sum = 0;
	for(int i = 0; x[i] != '\0'; i++)
		sum += x[i];

	return sum % cr_hashtable_SIZE;
}

void cr_insert(char* course, char* room)
{
	int key = cr_h(course);
	struct cr_tuple* tuple = new_cr_tuple(course, room);

	if(cr_hashtable[key] == NULL)
	{
		cr_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(cr_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(cr_hashtable[key], tuple))
			LinkedList_add_at_end(cr_hashtable[key], tuple);
	}
}

LinkedList cr_lookup(char* course, char* room)
{
	LinkedList set = new_LinkedList();

	if(strcmp(course, "*") != 0)
	{
		int key = cr_h(course);
		LinkedListIterator list_iterator = LinkedList_iterator(cr_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct cr_tuple* tuple = LinkedListIterator_next(list_iterator);

			int room_match = (strcmp(room, "*") == 0)||(strcmp(tuple->room, room)==0);
			int course_match = strcmp(tuple->course, course) == 0;

			if(room_match && course_match)
				LinkedList_add_at_end(set, tuple);
		}

		free(list_iterator);
		return set;
	}

	for(int i = 0; i < cr_hashtable_SIZE; i++)
	{
		if(cr_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(cr_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct cr_tuple* tuple = LinkedListIterator_next(list_iterator);

				int room_match = (strcmp(room, "*") == 0)||(strcmp(tuple->room, room)==0);

				if(room_match)
					LinkedList_add_at_end(set, tuple);
			}

			free(list_iterator);
		}
	}

	return set;
}

void cr_delete(char* course, char* room)
{
	if(strcmp(course, "*") != 0)
	{
		int key = cr_h(course);
		LinkedListIterator list_iterator = LinkedList_iterator(cr_hashtable[key]);
		while(LinkedListIterator_hasNext(list_iterator))
		{
			struct cr_tuple* tuple = LinkedListIterator_next(list_iterator);

			int room_match = (strcmp(room, "*") == 0)||(strcmp(tuple->room, room)==0);

			if(room_match)
				LinkedList_remove(cr_hashtable[key], tuple);
		}

		free(list_iterator);
		return;
	}

	for(int i = 0; i < cr_hashtable_SIZE; i++)
	{
		if(cr_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(cr_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct cr_tuple* tuple = LinkedListIterator_next(list_iterator);

				int room_match = (strcmp(room, "*") == 0)||(strcmp(tuple->room, room)==0);

				if(room_match)
					LinkedList_remove(cr_hashtable[i], tuple);
			}

			free(list_iterator);
		}
	}
}

void cr_free()
{
	for(int i = 0; i < cr_hashtable_SIZE; i++)
	{
		if(cr_hashtable[i] != NULL)
			LinkedList_free(cr_hashtable[i], 1);
	}
}

void cr_print(struct cr_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("Course: %s  Room: %s\n", tuple->course, tuple->room);
}

void cr_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct cr_tuple* tuple = LinkedListIterator_next(list_iterator);
		cr_print(tuple);
	}

	free(list_iterator);
}

void cr_print_all()
{
	for(int i = 0; i < cr_hashtable_SIZE; i++)
	{
		if(cr_hashtable[i] != NULL)
			cr_print_list(cr_hashtable[i]);
	}
}

/*
 * What grade did name get in course
 */
void nameCourse_to_grade(char* name, char* course)
{
	//for each tuple t in snap
		//if t has "Name" in its name component then
		//then let i be studentID component of tuple t
		//for each tuple s in csg
			//if s has course compnent "course" and studentid i then
			//print grade component

	for(int i = 0; i < snap_hashtable_SIZE; i++)
	{
		if(snap_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(snap_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct snap_tuple* tuple = LinkedListIterator_next(list_iterator);
				if(strcmp(tuple->name, name) == 0)
				{
					int i = tuple->studentID;
					for(int j = 0; j < csg_hashtable_SIZE; j++)
					{
						if(csg_hashtable[j] != NULL)
						{
							LinkedListIterator list_iterator2 = LinkedList_iterator(csg_hashtable[j]);
							while(LinkedListIterator_hasNext(list_iterator2))
							{
								struct csg_tuple* tuple2 = LinkedListIterator_next(list_iterator2);
								if((tuple2->studentID == i) && (strcmp(tuple2->course, course) == 0))
								{
									printf("%s got a %s in %s\n", name, tuple2->grade, course);
									free(list_iterator2);
									free(list_iterator);
									return;
								}
							}

							free(list_iterator2);
						}
					}
				}
			}

			free(list_iterator);
		}
	}

	printf("The grade for %s in %s was not found\n", name, course);
}

void nameHourDay_to_location(char* name, char* hour, char* day)
{
	LinkedList name_list = snap_lookup("*", name, "*", "*");

	if(LinkedList_isEmpty(name_list))
	{
		printf("The location of %s on %s at %s was not found\n", name, day, hour);
		return;
	}

	struct snap_tuple* snapTuple = LinkedList_elementAt(name_list, 0);
	int studentID = snapTuple->studentID;

	char id[5];
	sprintf(id, "%d", studentID);
	LinkedList courses_list = csg_lookup("*", id, "*");
	LinkedListIterator list_iterator = LinkedList_iterator(courses_list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct csg_tuple* csgTuple = LinkedListIterator_next(list_iterator);
		LinkedList dayTime_list = cdh_lookup(csgTuple->course, day, hour);

		if(!LinkedList_isEmpty(dayTime_list))
		{
			LinkedList location_list = cr_lookup(csgTuple->course, "*");
			if(!LinkedList_isEmpty(location_list))
			{
				struct cr_tuple* crTuple = LinkedList_elementAt(location_list, 0);
				printf("%s is at %s on %s at %s\n", name, crTuple->room, day, hour);
				free(list_iterator);
				return;
			}
		}
	}

	printf("The location of %s on %s at %s was not found\n", name, day, hour);
}

/*
 * Relational Algebra Functions
 */

//returns every tuple that doesn't have "course" as its course attribute
LinkedList csg_select_course(char* course)
{
	LinkedList set = new_LinkedList();

	for(int i = 0; i < csg_hashtable_SIZE; i++)
	{
		if(csg_hashtable[i] != NULL)
		{
			LinkedListIterator list_iterator = LinkedList_iterator(csg_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);
				if(strcmp(tuple->course, course) == 0)
					LinkedList_add_at_end(set, tuple);
			}
		}
	}

	return set;
}

typedef struct s_tuple* s_tupleList;
struct s_tuple {
	int studentID;
};
static int s_hashtable_SIZE = 10;
LinkedList s_hashtable[10];

struct s_tuple* new_s_tuple(int studentID)
{
	struct s_tuple* this = (struct s_tuple*)malloc(sizeof(struct s_tuple));

	if(this == NULL)
		return this;

	this->studentID = studentID;

	return this;
}

int s_h(int x)
{
	return x % s_hashtable_SIZE;
}

void s_insert(int studentID)
{
	int key = s_h(studentID);
	struct s_tuple* tuple = new_s_tuple(studentID);

	if(s_hashtable[key] == NULL)
	{
		s_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(s_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(s_hashtable[key], tuple))
			LinkedList_add_at_end(s_hashtable[key], tuple);
	}
}

void s_print(struct s_tuple* tuple)
{
	printf("StudentID: %d\n", tuple->studentID);
}

void s_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct s_tuple* tuple = LinkedListIterator_next(list_iterator);
		s_print(tuple);
	}
}

void s_print_all()
{
	for(int i = 0; i < s_hashtable_SIZE; i++)
	{
		if(s_hashtable[i] != NULL)
			s_print_list(s_hashtable[i]);
	}
}

void csg_projection_ID_select_course(char* course)
{
	LinkedList list = csg_select_course(course);
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct csg_tuple* tuple = LinkedListIterator_next(list_iterator);
		s_insert(tuple->studentID);
	}
}

typedef struct crdh_tuple* crdh_tupleList;
struct crdh_tuple {
	char* course;
	char* room;
	char* day;
	char* hour;
};
static int crdh_hashtable_SIZE = 7;
LinkedList crdh_hashtable[7];

struct crdh_tuple* new_crdh_tuple(char* course, char* room, char* day, char* hour)
{
	struct crdh_tuple* this = (struct crdh_tuple*)malloc(sizeof(struct crdh_tuple));

	if(this == NULL)
		return NULL;

	this->course = course;
	this->room = room;
	this->day = day;
	this->hour = hour;

	return this;
}

int crdh(char* x)
{
	int sum = 0;
	for(int i = 0; x[i] != '\0'; i++)
		sum += x[i];

	return sum % crdh_hashtable_SIZE;
}

void crdh_insert(char* course, char* room, char* day, char* hour)
{
	int key = crdh(day);
	struct crdh_tuple* tuple = new_crdh_tuple(course, room, day, hour);

	if(crdh_hashtable[key] == NULL)
	{
		crdh_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(crdh_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(crdh_hashtable[key], tuple))
			LinkedList_add_at_end(crdh_hashtable[key], tuple);
	}
}

void crdh_print(struct crdh_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("Course: %s  Room: %s  Day: %s  Room: %s\n", tuple->course, tuple->room, tuple->day, tuple->hour);
}

void crdh_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct crdh_tuple* tuple = LinkedListIterator_next(list_iterator);
		crdh_print(tuple);
	}

	free(list_iterator);
}

void crdh_print_all()
{
	for(int i = 0; i < crdh_hashtable_SIZE; i++)
	{
		if(crdh_hashtable[i] != NULL)
			crdh_print_list(crdh_hashtable[i]);
	}
}

void cr_join_cdh_course()
{
	for(int i = 0; i < cr_hashtable_SIZE; i++)
	{
		if(cr_hashtable[i] != NULL)
		{
			LinkedListIterator cr_list_iterator = LinkedList_iterator(cr_hashtable[i]);
			while(LinkedListIterator_hasNext(cr_list_iterator))
			{
				struct cr_tuple* crTuple = LinkedListIterator_next(cr_list_iterator);

				for(int j = 0; j < cdh_hashtable_SIZE; j++)
				{
					if(cdh_hashtable[j] != NULL)
					{
						LinkedListIterator cdh_list_iterator = LinkedList_iterator(cdh_hashtable[j]);
						while(LinkedListIterator_hasNext(cdh_list_iterator))
						{
							struct cdh_tuple* cdhTuple = LinkedListIterator_next(cdh_list_iterator);

							if(strcmp(crTuple->course, cdhTuple->course) == 0)
							{
								crdh_insert(crTuple->course, crTuple->room, cdhTuple->day, cdhTuple->hour);
							}
						}


					}
				}
			}
		}
	}
}


struct dh_tuple {
	char* day;
	char* hour;
};
static int dh_hashtable_SIZE = 5;
LinkedList dh_hashtable[5];

struct dh_tuple* new_dh_tuple(char* day, char* hour)
{
	struct dh_tuple* this = (struct dh_tuple*)malloc(sizeof(struct dh_tuple));

	if(this == NULL)
		return NULL;

	this->day = day;
	this->hour = hour;

	return this;
}

int dh_h(char* x)
{
	int sum = 0;
	for(int i = 0; x[i] != '\0'; i++)
		sum += x[i];

	return sum % dh_hashtable_SIZE;
}

void dh_insert(char* day, char* hour)
{
	int key = dh_h(day);
	struct dh_tuple* tuple = new_dh_tuple(day, hour);

	if(dh_hashtable[key] == NULL)
	{
		dh_hashtable[key] = new_LinkedList();
		LinkedList_add_at_end(dh_hashtable[key], tuple);
	}
	else
	{
		if(!LinkedList_contains(dh_hashtable[key], tuple))
			LinkedList_add_at_end(dh_hashtable[key], tuple);
	}
}

void dh_print(struct dh_tuple* tuple)
{
	if(tuple == NULL)
		printf("tuple null\n");
	else
		printf("Day: %s  Hour: %s\n", tuple->day, tuple->hour);
}

void dh_print_list(LinkedList list)
{
	LinkedListIterator list_iterator = LinkedList_iterator(list);
	while(LinkedListIterator_hasNext(list_iterator))
	{
		struct dh_tuple* tuple = LinkedListIterator_next(list_iterator);
		dh_print(tuple);
	}
}

void dh_print_all()
{
	for(int i = 0; i < dh_hashtable_SIZE; i++)
	{
		if(dh_hashtable[i] != NULL)
			dh_print_list(dh_hashtable[i]);
	}
}

void cr_join_cdh_select_room_project_dayHour(char* room)
{
	cr_join_cdh_course();
	LinkedListIterator list_iterator;

	for(int i = 0; i < crdh_hashtable_SIZE; i++)
	{
		if(crdh_hashtable[i] != NULL)
		{
			list_iterator = LinkedList_iterator(crdh_hashtable[i]);
			while(LinkedListIterator_hasNext(list_iterator))
			{
				struct crdh_tuple* tuple = LinkedListIterator_next(list_iterator);

				if(strcmp(tuple->room, room) == 0)
				{
					dh_insert(tuple->day, tuple->hour);
				}
			}
		}
	}
}
