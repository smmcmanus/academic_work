// McManusLab1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "standard.h"
#include "pieces.h"
#include "board.h"
#include <string>
#include <fstream>
#include <iostream>


int main(int argc, char* argv[])
{
	if(argc != 2){
		usage_message(argv[program_name], " Not enough arguments", too_few_arguments);
		cerr << too_few_arguments << endl;
		return too_few_arguments;
	}
	ifstream main_stream;
	main_stream.open(argv[file_name]);
	if(main_stream.is_open()){
		unsigned int width = 0;
		unsigned int height = 0;
		int read_status = read_dimensions(main_stream, width, height);
		if(read_status == failed_to_read_one_dimension || read_status == failed_to_read_both_dimensions){
			while(read_status != success && read_status != failed_to_read_line && read_status != input_stream_not_open){
				read_status = read_dimensions(main_stream, width, height);
			}
		}
		if(read_status == failed_to_read_line){
			cout << "No Lines Read In" << endl;
			cerr << failed_to_read_line;
			return failed_to_read_line;
		} else if(read_status == input_stream_not_open){
			cout << "Input Stream Not Open" << endl;
			cerr << input_stream_not_open << endl;
			return input_stream_not_open;
		}else if(read_status == success){
			vector<game_piece> pieces;
			game_piece a = game_piece();
			for(unsigned i = 0; i < (width * height); ++i){
				pieces.push_back(a);
			}
			int pieces_status = read_in_pieces(main_stream, pieces, width, height);
			if(pieces_status != success){
				cout << "No Pieces Read In" << endl;
				cerr << pieces_status << endl;
				return pieces_status;
			} else {
				return print_board(pieces, width, height);
			}
		}

	} else {
		cout << "Cannot Open File" << endl;
		cerr << cannot_open_file << endl;
		return cannot_open_file;
	}
}

