/*
 * relationalDataModel.c
 *
 *  Created on: Nov 15, 2021
 *      Author: jzeng
 */
#include "hashtable.h"
#include "LinkedList.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void populateDatabase()
{
	csg_insert("CS101", 12345, "A");
	csg_insert("CS101", 67890, "B");
	csg_insert("EE200", 12345, "C");
	csg_insert("EE200", 22222, "B+");
	csg_insert("CS101", 33333, "A-");
	csg_insert("PH100", 67890, "C+");

	snap_insert(12345, "C Brown", "12 Apple St", "555-1234");
	snap_insert(67890, "L Van Pelt", "34 Pear Ave", "555-5678");
	snap_insert(22222, "P Patty", "56 Grape Blvd", "555-9999");

	cp_insert("CS101", "CS100");
	cp_insert("EE200", "EE105");
	cp_insert("EE200", "CS100");
	cp_insert("CS120", "CS101");
	cp_insert("CS121", "CS120");
	cp_insert("CS205", "CS101");
	cp_insert("CS206", "CS121");
	cp_insert("CS206", "CS205");

	cdh_insert("CS101", "M", "9am");
	cdh_insert("CS101", "W", "9am");
	cdh_insert("CS101", "F", "9am");
	cdh_insert("EE200", "Tu", "10am");
	cdh_insert("EE200", "W", "1pm");
	cdh_insert("EE200", "Th", "10am");

	cr_insert("CS101", "Turing Aud");
	cr_insert("EE200", "25 Ohm Hall");
	cr_insert("PH100", "Newton Lab");
}

void free_all()
{
	csg_free();
	snap_free();
	cp_free();
	cdh_free();
	cr_free();
}

void part1()
{
	populateDatabase();
	printf("*** Testing the course-studentID-grade relation ***\n");
	printf("\ninital database: \n");
	csg_print_all();
	printf("\nClasses that studentID 12345 is taking: \n");
	csg_print_list(csg_lookup("*", "12345", "*"));
	printf("\nAll studentIDs that are taking CS101\n");
	csg_print_list(csg_lookup("CS101", "*", "*"));
	printf("\ndatabase after removing class EE200\n");
	csg_delete("EE200", "*", "*");
	csg_print_all();
	csg_delete("*", "*", "*");

	printf("\n*** Testing the studentID-name-address-phone relation ***\n");
	printf("\ninitial database: \n");
	snap_print_all();
	printf("\nC Brown's entry: \n");
	snap_print_list(snap_lookup("*", "C Brown", "*", "*"));
	printf("\ndatabase after removing C Brown's entry: \n");
	snap_delete("*", "C Brown", "*", "*");
	snap_print_all();
	snap_delete("*", "*", "*", "*");

	printf("\n*** Testing the course-prereq relation ***\n");
	printf("\ninitial database: \n");
	cp_print_all();
	printf("\nAll of CS206's prereqs:\n");
	cp_print_list(cp_lookup("CS206", "*"));
	printf("\ndatabase after removing the CS101 prereq for all courses:\n");
	cp_delete("*", "CS101");
	cp_print_all();
	cp_delete("*", "*");

	printf("\n*** Testing the course-day-hour relation ***\n");
	printf("\ninitial database:\n");
	cdh_print_all();
	printf("\nall classes on wednesday:\n");
	cdh_print_list(cdh_lookup("*", "W", "*"));
	printf("\ndatabase after removing EE200:\n");
	cdh_delete("EE200", "*", "*");
	cdh_print_all();
	cdh_delete("*", "*", "*");

	printf("\n*** Testing the course-room relation ***\n");
	printf("\ninitial database:\n");
	cr_print_all();
	printf("\nCS101's location:\n");
	cr_print_list(cr_lookup("CS101", "*"));
	printf("\ndatabase after removing classes in Newton Lab:\n");
	cr_delete("*", "Newton Lab");
	cr_print_all();
	cr_delete("*", "*");
}

void part2()
{
	populateDatabase();

	printf("\n*** testing nameCourse_to_grade ***\n");
	printf("Enter a name(or enter a ! to quit): \n");
	fflush(stdout);
	char str1[256];
	char str2[256];
	char* name;
	char* course;

	while(strcmp(fgets(str1, 255, stdin), "!\n") != 0)
	{
		name = &str1[0];
		name[strcspn(name,"\n")] = 0;
		printf("Enter a course code: \n");
		fflush(stdout);
		fgets(str2, 255, stdin);
		course = &str2[0];
		course[strcspn(course,"\n")] = 0;
		nameCourse_to_grade(name, course);
		printf("Enter a name(or enter a ! to quit): \n");
		fflush(stdout);
	}

	printf("\n*** testing nameHourDay_to_location ***\n");
	printf("Enter a name(or enter a ! to quit): \n");
	fflush(stdout);
	char str3[256];
	char str4[256];
	char str5[256];
	char* hour;
	char* day;

	while(strcmp(fgets(str3, 255, stdin), "!\n") != 0)
	{
		name = &str3[0];
		name[strcspn(name,"\n")] = 0;
		printf("Enter a hour: \n");
		fflush(stdout);
		fgets(str4, 255, stdin);
		hour = &str4[0];
		hour[strcspn(hour,"\n")] = 0;
		printf("Enter a day: \n");
		fflush(stdout);
		fgets(str5, 255, stdin);
		day = &str5[0];
		day[strcspn(day, "\n")] = 0;
		nameHourDay_to_location(name, hour, day);
		printf("Enter a name(or enter a ! to quit): \n");
		fflush(stdout);
	}

}

void part3()
{
	printf("\n*** Rational Algebra Examples ***\n");
	printf("select example(8.12): \n");
	csg_print_list(csg_select_course("CS101"));
	printf("\nprojection example(8.13): \n");
	csg_projection_ID_select_course("CS101");
	s_print_all();
	printf("\njoin example(8.14): \n");
	cr_join_cdh_select_room_project_dayHour("Turing Aud");
	crdh_print_all();
	printf("\nall three example(8.15): \n");
	dh_print_all();
}

int main(int argc, char *argv[])
{
	part1();
	part2();
	part3();
}
