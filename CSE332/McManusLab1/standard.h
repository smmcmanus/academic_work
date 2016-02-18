#include <string>
using namespace std;

enum argument_indices{program_name, file_name, argument_count};

enum results{ success, too_few_arguments, cannot_open_file, failed_to_read_line, failed_to_read_one_dimension, failed_to_read_both_dimensions, input_stream_not_open, no_well_formed_pieces, too_many_pieces};

int usage_message(const string &, const string &, int);

void lowercase(string &);