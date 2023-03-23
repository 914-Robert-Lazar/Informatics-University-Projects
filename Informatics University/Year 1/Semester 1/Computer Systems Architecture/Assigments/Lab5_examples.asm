bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a db 1,2,3,4,5
    lenZ1 equ $-a 
    b db 1,2,3,4,5
    lenZ2 equ $-b
    lenZ3 equ $-a
    c dw 1,2,3,4,5
    lenA3 equ $-c
    lenB1 equ $-b
    lenA1 equ $-a 
    lenA2 equ $-b 
    lenB2 equ $-c
    lenB3 equ $-b - c 
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, lenZ1
        mov al, lenZ2
        mov al, lenZ3
        mov al, lenA1
        mov al, lenA2
        mov al, lenA3
        mov al, lenB1
        mov al, lenB2
        mov al, lenB3
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
