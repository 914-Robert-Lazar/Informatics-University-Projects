bits 32

global _compute
segment code public code use32
_compute:
    push ebp
    mov ebp, esp
    
    mov eax, [ebp + 8]
    add eax, [ebp + 12]
    sub eax, [ebp + 16]
    
    mov esp, ebp
    pop ebp
    
    ret