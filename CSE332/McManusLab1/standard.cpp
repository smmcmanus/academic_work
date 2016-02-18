#include "stdafx.h"
#include "standard.h"
#include "pieces.h"
#include "board.h"
#include <string>
#include <iostream>
using namespace std;

int usage_message (const string &program_name, const string &message, int error)
{
	cout << program_name << endl;
	cout << message << endl;
	cout << "usage: McManusLab1 <input_file_name>" << endl;
	return error;
}

void lowercase(string & original){
	for(auto a = original.begin(); a != original.end(); ++a){
		if(*a < 91 && *a > 64){
			*a += 32;
		}
	}
}