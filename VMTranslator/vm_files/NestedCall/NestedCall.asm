//Bootstrap code
@256
D=A
@0
M=D
//call Sys.init 0
@NestedCall$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D

@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(NestedCall$ret.0)
//function Sys.init 0
(Sys.init)
//C_PUSH constant 4000
@4000
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
//C_PUSH constant 5000
@5000
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//call Sys.main 0
@Sys$ret.1
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D

@SP
D=M
@LCL
M=D
@Sys.main
0;JMP
(Sys$ret.1)
//C_POP temp 1
@1
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
// Label LOOP
(LOOP)
// goto LOOP
@LOOP
0;JMP
//function Sys.main 5
(Sys.main)
//C_PUSH constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_PUSH constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_PUSH constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_PUSH constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_PUSH constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_PUSH constant 4001
@4001
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
//C_PUSH constant 5001
@5001
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//C_PUSH constant 200
@200
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP local 1
@1
D=A
@LCL
D=M+D
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//C_PUSH constant 40
@40
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP local 2
@2
D=A
@LCL
D=M+D
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//C_PUSH constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP local 3
@3
D=A
@LCL
D=M+D
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//C_PUSH constant 123
@123
D=A
@SP
A=M
M=D
@SP
M=M+1
//call Sys.add12 1
@Sys$ret.2
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@1
D=A
@5
D=D+A
@SP
D=M-D
@ARG
M=D

@SP
D=M
@LCL
M=D
@Sys.add12
0;JMP
(Sys$ret.2)
//C_POP temp 0
@0
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//C_PUSH local 0
@0
D=A
@LCL
D=M+D
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
D=M
M=D+1
//C_PUSH local 1
@1
D=A
@LCL
D=M+D
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
D=M
M=D+1
//C_PUSH local 2
@2
D=A
@LCL
D=M+D
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
D=M
M=D+1
//C_PUSH local 3
@3
D=A
@LCL
D=M+D
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
D=M
M=D+1
//C_PUSH local 4
@4
D=A
@LCL
D=M+D
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
D=M
M=D+1
//add
@SP
AM=M-1
D=M
A=A-1
MD=D+M
//add
@SP
AM=M-1
D=M
A=A-1
MD=D+M
//add
@SP
AM=M-1
D=M
A=A-1
MD=D+M
//add
@SP
AM=M-1
D=M
A=A-1
MD=D+M
//return
@LCL
D=M
@R13
M=D
@R13
D=M
@5
A=D-A
D=M
@R14
M=D
@SP
AM=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@R13
D=M
@1
A=D-A
D=M
@THAT
M=D
@R13
D=M
@2
A=D-A
D=M
@THIS
M=D
@R13
D=M
@3
A=D-A
D=M
@ARG
M=D
@R13
D=M
@4
A=D-A
D=M
@LCL
M=D
@R14
A=M
0;JMP
//function Sys.add12 0
(Sys.add12)
//C_PUSH constant 4002
@4002
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
//C_PUSH constant 5002
@5002
D=A
@SP
A=M
M=D
@SP
M=M+1
//C_POP pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//C_PUSH argument 0
@0
D=A
@ARG
D=M+D
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
D=M
M=D+1
//C_PUSH constant 12
@12
D=A
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
AM=M-1
D=M
A=A-1
MD=D+M
//return
@LCL
D=M
@R13
M=D
@R13
D=M
@5
A=D-A
D=M
@R14
M=D
@SP
AM=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@R13
D=M
@1
A=D-A
D=M
@THAT
M=D
@R13
D=M
@2
A=D-A
D=M
@THIS
M=D
@R13
D=M
@3
A=D-A
D=M
@ARG
M=D
@R13
D=M
@4
A=D-A
D=M
@LCL
M=D
@R14
A=M
0;JMP
