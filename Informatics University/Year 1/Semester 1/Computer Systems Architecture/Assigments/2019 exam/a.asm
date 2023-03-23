bits 32

global start
global ranks
global sum

extern printf
extern Compute
extern exit
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    string dd 1234A678h, 12345678h, 1AC3B47Dh, 0FEDC9876h, 0
    ranks times 5 db 0
    sum dd 0
    printTable db '01234', 0
    printFormat db 'String of byte ranks: %s, the sum: %d', 0
segment code use32 class=code
    start:
        mov ebx, printTable
        push string
        call Compute
        add esp, 4
        
        mov al, [sum]
        cbw
        cwde
        
        push dword eax
        push dword ranks
        push dword printFormat
        call [printf]
        
        push dword 0
        call [exit]
        
        