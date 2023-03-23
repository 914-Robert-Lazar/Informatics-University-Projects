bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 
    ; ...
    a dd 0
    b dd 0
    c dd 0
    format db "%d %d %d", 0
    message db "The result of a + b - c = %d", 0
; our code starts here
segment code use32 public code
    start:
        ; ...
        
        push dword c
        push dword b
        push dword a 
        push dword format 
        call [scanf]
        add esp, 4 * 4
        
        ;push dword c 
        ;push dword b 
        ;push dword a 
        ;call compute
        mov eax, [a]
        add eax, [b]
        sub eax, [c]
        
        push eax
        push message
        call [printf]
        add esp, 4 * 2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
