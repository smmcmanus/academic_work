#include <string>
#include <vector>
#include <fstream>
#include <sstream>
using namespace std;

int read_dimensions(ifstream &, unsigned int&, unsigned int&);

int read_in_pieces(ifstream &, vector<game_piece> &, unsigned int, unsigned int);

int print_board(const vector <game_piece> &, unsigned int, unsigned int);