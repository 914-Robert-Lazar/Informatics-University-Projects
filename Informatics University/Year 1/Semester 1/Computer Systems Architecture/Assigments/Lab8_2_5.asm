bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fread, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll 
import fread msvcrt.dll 
import printf msvcrt.dll  
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "exercise5.txt", 0
    access_mode db "r", 0
    
    file_descriptor dd -1
    no_read_car dd 0
    len equ 100
    text times len db 0
    format db "The number of special characters from the file: %d", 0
    sol dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 4 * 2
        
        cmp eax, 0
        je toEnd 
        
        mov [file_descriptor], eax
        
        while:
            push dword [file_descriptor]
            push dword len
            push dword 1
            push dword text
            call [fread]
            add esp, 4 * 4
            
            cmp eax, 0
            je endOfWhile
            
            mov [no_read_car], eax
            
            mov ecx, [no_read_car]
            mov esi, text
            forText:
                lodsb
                
                cmp al, '0'
                jl found_spechar
                cmp al, '9'
                jle next_char
                cmp al, 'A'
                jl found_spechar
                cmp al, 'Z'
                jle next_char
                cmp al, 'a'
                jl found_spechar
                cmp al, 'z'
                jle next_char
                
                found_spechar:
                    inc dword [sol]
                next_char:
            loop forText
            
        jmp while
        endOfWhile:
        push dword [sol]
        push dword format
        call [printf]
        add esp, 4 * 2
        
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        toEnd:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
