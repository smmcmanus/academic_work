#include "stdafx.h"
#include "standard.h"
#include "pieces.h"
#include "board.h"
#include <string>
using namespace std;

string piece_to_string(piece_color c){
	switch(c){
	case red:
		return "red";
	case black:
		return "black";
	case white:
		return "white";
	case no_color:
		return "no color";
	case invalid_color:
		return "invalid color";
	}
	return "should not reach this"; 
}

piece_color string_to_piece(string color){
	lowercase(color);
	if(color == "red"){
		return red;
	} else if (color == "black"){
		return black;
	} else if (color == "white"){
		return white;
	} else if(color == "" || color == " " || color == "no color"){
		return no_color;
	} else return invalid_color;
}


game_piece::game_piece()
	: color(no_color), name(""), display(" "){}
game_piece::game_piece(piece_color color, string name, string display)
	:color(color), name(name), display(display){}

