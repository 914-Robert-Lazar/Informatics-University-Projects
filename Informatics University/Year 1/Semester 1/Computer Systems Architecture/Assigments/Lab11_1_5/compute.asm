bits 32

segment code use32 public code
global compute

compute:
    mov eax, [esp + 4]
    add eax, [esp + 8]
    sub eax, [esp + 12]
    
    ret 3 * 4