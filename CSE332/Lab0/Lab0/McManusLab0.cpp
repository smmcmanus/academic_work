/*
*Lab 0
*Sean McManus
*431817
*CSE 332s
*/


#include "stdafx.h"
#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <fstream>
#include "McManusLab0.h"


int main(int argc, char * argv[])
{
	if( argc != 2 ){
		usage_message( argv[program_name], too_few_arguments); 
	}
	vector<string> vs;
	parse_file(vs, argv[file_name]);
	vector<int> vi;
	for(auto a = vs.begin(); a != vs.end(); ++a) {
		bool integer = true;
		for(auto letter = a -> begin(); letter != a -> end(); ++letter){
			if(!isdigit(letter)){
				integer = false;
			}
		}
		int i;
		if(integer){
			istringstream string_stream (*a);
			string_stream >> i;
			vi.push_back(i);
		} else {
			cout << *a << endl;
		}
	}
	for (size_t j = 0; j < vi.size(); ++j){
		cout << vi[j] << endl;
	}
	return success;
}

int parse_file (vector<string> &a, char * file)
{
	ifstream in(file);

	if(in.is_open()){
		string line, word;
		while(getline(in, line)){
			istringstream string_stream(line);
			while (string_stream >> word){
				a.push_back(word);
			}
		}
	} else {
		cout << "File cannot be opened" << endl;
		return cannot_open_file;
	}
	            

	return success;
}

int usage_message (const string &program_name, int error)
{
	cout << program_name << endl;
	cout << "usage: lab0 <input_file_name>" << endl;
	return error;
}
