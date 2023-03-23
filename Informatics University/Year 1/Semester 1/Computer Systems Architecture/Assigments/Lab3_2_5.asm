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
    a db -46h
    b dw 4569h
    c dd -49999887h
    d dq 4999999999999453h
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, [a]
        cbw
        cwde
        cdq ;edx:eax = a
        add eax, [d]
        adc edx, [d + 4]
        push edx
        push eax
        mov eax, [c]
        cdq ;edx:eax = c
        pop ebx
        pop ecx
        add ebx, eax
        adc ecx, edx ;ecx:ebx = c + a + d
        mov ax, [b]
        add ax, [b]
        cwde ;eax= b + b
        add eax, [c]
        cdq ;edx:eax = c + b + b 
        sub eax, ebx
        sbb edx, ecx ;edx:eax = (c + b + b) - (c + a + d)
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
