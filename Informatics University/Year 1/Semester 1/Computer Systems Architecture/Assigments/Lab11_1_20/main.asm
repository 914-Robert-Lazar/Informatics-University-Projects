bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               ; tell nasm that exit exists even if we won't be defining it
extern prime
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format db "%d", 0
    message db "The prime numbers: ", 0
    errorMessage db "ECX = %d ", 0
    littleMessage db "%d " , 0
    n dd 0
    valueOfEcx dd 0
    list dd 0
; our code starts here
segment code use32 public class=code
    start:
        ; ...
        
        push message
        call [printf]
        add esp, 4
        
        push dword n
        push dword format
        call [scanf]
        add esp, 4 * 2
        
        mov ecx, [n]
        cmp ecx, 0
        je ending
        mov edi, list
    
        reading:
            push ecx
            
            push dword edi
            push dword format
            call [scanf]
            add esp, 4 * 2
            
            add edi, 4
            
            pop ecx
        loop reading
        
        push message
        call [printf]
        add esp, 4
        
        mov ecx, [n]
        jecxz ending
        mov esi, list
        
        writing:
            lodsd
            
            mov [valueOfEcx], ecx
            
            push dword eax
            call prime
            
            mov ecx, [valueOfEcx]
        loop writing
        ending:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
