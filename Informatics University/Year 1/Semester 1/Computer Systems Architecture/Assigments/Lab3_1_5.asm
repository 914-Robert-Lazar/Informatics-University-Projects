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
    a db 96h
    b dw 8569h
    c dd 99999987h
    d dq 9999999999999453h
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov eax, [c]
        sub al, [a]
        sbb ah, 0
        mov edx, 0
        sub eax, dword [d]
        sbb edx, dword [d + 4]
        mov ebx, 0
        mov bx, word [c]
        mov cx, word [c + 2]
        sub bx, [b]
        sbb cx, 0
        sub bl, [a]
        sbb bh, 0
        shl cx, 16
        add eax, ebx
        adc eax, ecx
        
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
