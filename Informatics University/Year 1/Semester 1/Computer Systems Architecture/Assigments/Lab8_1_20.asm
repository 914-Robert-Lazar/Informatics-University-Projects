bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf       ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    message db "sum = %x \n difference = %x", 0
    format db "%x %x", 0
    a dd 0
    b dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword b 
        push dword a
        push format
        call [scanf]
        add esp, 4 * 3
        
        mov ax, [a + 2]
        sub ax, [b + 2]
        cwd
        push dword eax
        
        mov ax, [a]
        add ax, [b]
        push dword eax
        
        push dword message
        call [printf]
        add esp, 4 * 3
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
