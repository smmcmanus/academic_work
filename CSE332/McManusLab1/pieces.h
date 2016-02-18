#include <string>
using namespace std;

enum piece_color{red, black, white, invalid_color, no_color};

string piece_to_string(piece_color);

piece_color string_to_piece(string);

struct game_piece {
	piece_color color;
	string name;
	string display;

	game_piece();
	game_piece(piece_color, string, string);
};