CSE 332s 
Lab 1
Sean McManus
431817

-Errors: Vector subscript out of range error. This error was reached when I tried to access an index of the board that didn't 
	exist due to mistakes in loop variable ranges.
	Unresolved external Symbol error. A function declaration did not match the definition  
	Multiple undeclared identifier errors. I had not included all the header files necessary in other files.


Trials and Outputs:
1. McManusLab1.exe
Output:
Not Enough Arguments
usage: McManusLab1 <input_file_name>
1

This is correct output
2. McManusLab1.exe a.txt (a.txt does not exist)
Cannot Open File
2

This is correct output
3. McManusLab1.exe tic-tac-toe.txt
Contents:
3 3
red Xs X 0 0
red Xs X 0 2
red Xs X 1 1
red Xs X 1 2
red Xs X 2 2
black Os O 0 1 
black Os O 1 0 
black Os O 2 0 
black Os O 2 2 

Ouput:
X|X|O
O|X|
X|O|O

This is correct output

4. McManusLab1.exe tic-tac-toe.txt
Contents:
3 3
raaed Xs X 0 0
red Xs X 0 2

red Xs X 1 1
red Xs X 1 2

red Xs X 2 2
black Os O 0 1 
black Os O 1 0 
black Os O 2 0 
black Os O 2 2627 

Ouput:
X|X|O
O|X|
 |O|X

This is correct output

5. McManusLab1.exe empty.txt
Output:
No Lines Read In
3
This is correct output

6. McManusLab1.exe gomoku.txt
Contents:
8 8
black stone X 0 0
black stone X 0 2
black stone X 1 1
black stone X 2 0
black stone X 2 2
black stone X 3 1
black stone X 4 0
black stone X 4 2
black stone X 5 1
black stone X 6 0
black stone X 6 2
black stone X 7 1
white stone O 0 6
white stone O 1 5
white stone O 1 7
white stone O 2 6
white stone O 3 5
white stone O 3 7
white stone O 4 6
white stone O 5 5
white stone O 5 7
white stone O 6 6
white stone O 7 5
white stone O 7 7
Output:
 |O| |O| |O| |O
O| |O| |O| |O|
 |O| |O| |O| |O
 | | | | | | |
 | | | | | | | 
X| |X| |X| |X| 
 |X| |X| |X| |X
X| |X| |X| |X| 

This is correct output.