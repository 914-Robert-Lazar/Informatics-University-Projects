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
    a dw 1125h
    b db -16h
    e dd -2999887h
    x dq 99999999999453h
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [a]
        mov bx, 2
        mul bx ;dx:ax = 2 * a
        mov bx, 0
        mov bl, [b]
        sub ax, bx
        sbb dx, 0
        push dx
        push ax
        mov ax, 0
        mov al, [b]
        mul byte [b] ;ax = b * b
        mov bx, ax
        pop ax
        pop dx
        div bx; ax = (2 * a - b) / (b * b)
        sub al, [b]
        sbb ah, 0
        add al, 8
        adc ah, 0 ;ax = (2 * a - b) / (b * b) - b + 8
        mov ebx, 0
        mov bx, ax
        add ebx, [e]
        mov eax, ebx
        mov edx, 0
        add eax, [x]
        adc edx, [x + 4] ; edx:eax = x - b + 8 + (2 * a - b) / (b * b) + e
        
        
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
