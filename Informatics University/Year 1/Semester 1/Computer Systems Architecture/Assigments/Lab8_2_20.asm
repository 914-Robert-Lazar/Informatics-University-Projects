bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "exercise20.txt", 0
    acces_mode db "w", 0
    
    file_descriptor dd -1
    TabDigit db "0123456789ABCDEF"
    text db "as d  v", 0
    len equ $ - text
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, len
        mov edi, text
        mov ebx, TabDigit
        
        jecxz stop
        forText:
            inc edi
            mov eax, edi
            sub eax, text
            inc al
            xlat
            
            stosb
            dec ecx
        loop forText
        
        push dword acces_mode
        push dword file_name
        call [fopen]
        add esp, 4 * 2
        
        cmp eax, 0
        je stop2
        
        mov [file_descriptor], eax
        
        push dword text
        push dword [file_descriptor]
        call [fprintf]
        add esp, 4 * 2
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        stop:
        stop2:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
