/*
*Lab 0
*Sean McManus
*431817
*CSE 332s
*/

using namespace std;

enum argument_indices{program_name, file_name, argument_count};

enum results{ success = 0, too_few_arguments = 1, cannot_open_file = 2};

int parse_file (vector<string> &, char *);

int usage_message(const string &, int);


