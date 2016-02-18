#include "stdafx.h"
#include "standard.h"
#include "pieces.h"
#include "board.h"
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
using namespace std;

int read_dimensions(ifstream & ifs, unsigned int &width, unsigned int &height){
	unsigned number_extracted = 0;
	if(ifs.is_open()){
		string c;
		if(getline(ifs, c)){
			istringstream int_stream (c);
			if(int_stream >> width){
				number_extracted++;
			}
			if(int_stream >> height){
				number_extracted++;
			}
		} else{
			return failed_to_read_line;
		}
		if(number_extracted == 2){
			return success;
		} else if(number_extracted == 1){
			return failed_to_read_one_dimension;
		} else {
			return failed_to_read_both_dimensions;
		}
	} else {
		return input_stream_not_open;
	}

}

int read_in_pieces(ifstream & ifs, vector<game_piece> & pieces, unsigned int width, unsigned int height){
	int read_counter = 0;
	bool correct = false;
	int num_correct = 0;
	int index;
	if(ifs.is_open()){
		string c;
		while(getline(ifs, c)){
			istringstream in_stream (c);
			string color;
			string name;
			string display;
			piece_color pc;
			unsigned int horizontal = 0;
			unsigned int vertical = 0;
			if(in_stream >> color){
				if(in_stream >> name){
					if(in_stream >> display){
						if(in_stream >> horizontal){
							if(in_stream >> vertical){
								pc = string_to_piece(color);
								if(horizontal < width && vertical < height && pc != invalid_color){
									num_correct++;
									index = width * vertical + horizontal;
									game_piece gp = game_piece(pc, display, name);
									pieces[index].color = pc;
									pieces[index].display = display;
									pieces[index].name = name;
								}
							}
						}
					}
				}
			}
		}

	}
	if(num_correct > 0){
		return success;
	} else {
		return no_well_formed_pieces;
	}
}

int print_board(const vector<game_piece> & pieces, unsigned int width, unsigned int height){
	unsigned size = pieces.size();
	if(size > width * height){
		cout << "Too many pieces for given game board dimensions" << endl;
		cerr << too_many_pieces << endl;
		return too_many_pieces;
	} else {
		for(int i = (int)height -1; i >= 0; --i){
			for(unsigned j = 0; j < width; ++ j){
				if(j == width - 1){
					cout << pieces[((width) * i + j)].display << endl;
				} else {
					cout << pieces[((width) * i + j)].display << "|";
				}
			}
		}
		return success;
	}
}