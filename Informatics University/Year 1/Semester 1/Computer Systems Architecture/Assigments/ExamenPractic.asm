bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fopen, fread, fclose, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll  
import scanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fread msvcrt.dll  ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    fileName resb 15
    scanFormat db "%s", 0
    accessMode db "r", 0
    file_descriptor dd -1
    text resb 100
    printFormat db "The words at odd position: %s", 0
    solution resb 100
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;reads the name of the file
        push dword fileName
        push dword scanFormat
        call [scanf]
        add esp, 4 * 2
        
        ;opens the file
        push dword accessMode
        push dword fileName
        call [fopen]
        add esp, 4 * 2
        
        cmp eax, 0
        je end_of_program
        
        mov [file_descriptor], eax
        
        ;reads and saves the content of the file in the variable text
        push dword [file_descriptor]
        push dword 100
        push dword 1
        push text
        call [fread]
        add esp, 4 * 4
        
        ;closes the file
        push dword [file_descriptor]
        call [fclose]
        add esp, 4 * 1
        
        ;Determines the words at odd index and saves them in the solution
        mov ebx, 0
        mov esi, text
        mov edi, solution
        getOddIndexWords:
            lodsb
            cmp al, 0
            je done
            cmp ebx, 0
            je skip_saving
                stosb
            skip_saving:
            cmp al, " "
            jne skip_change
                cmp ebx, 0
                je change_to_1
                    mov ebx, 0
                    jmp skip_change_to_1
                change_to_1:
                    mov ebx, 1
                skip_change_to_1:
            skip_change:
        jmp getOddIndexWords
        done:   
            stosb
        ;Prints the solution to the screen
        push solution
        push printFormat
        call [printf]
        add esp, 4 * 2
        end_of_program:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
