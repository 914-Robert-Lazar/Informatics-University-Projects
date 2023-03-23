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
    a db -16h
    b dw 3569h
    c dd -29999887h
    d dq 4999999999999453h
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, [a]
        cbw ;ax = a 
        sub ax, [b]
        mov bx, ax ;bx = a - b 
        mov eax, [c]
        cdq ;edx:eax = c 
        sub eax, [d]
        sbb edx, [d + 4]; edx:eax = c - d 
        mov ecx, eax
        mov ax, bx
        cwde
        mov ebx, edx ; ebx:ecx = c - d
        cdq ;edx:eax = a - b
        sub eax, ecx
        sbb edx, ebx ; edx:eax = a - b - (c - d)
        add eax, [d]
        adc edx, [d + 4] ;edx: eax = a - b - (c - d) + d
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
