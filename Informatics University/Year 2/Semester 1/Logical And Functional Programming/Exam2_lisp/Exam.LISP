(defun mergeLists (l1 l2)
  (cond
   ((null l1) l2)
   ((null l2) l1)
   ((< (car l1) (car l2)) (cons (car l1) (mergeWithoutDuplicates (cdr l1) l2)))
   ((< (car l2) (car l1)) (cons (car l2) (mergeWithoutDuplicates l1 (cdr l2))))
   (t (cons (car l1) (mergeWithoutDuplicates (cdr l1) (cdr l2))))
   )
)

(defun removeDuplicate(l prev)
  (cond
   ((null l) nil)
   ((/= prev (car l)) (cons (car l) (removeDuplicate (cdr l) (car l))))
   (t (removeDuplicate (cdr l) (car l)))
   )
)

(defun mergeWithoutDuplicates (l1 l2)
  (removeDuplicate (mergeLists l1 l2) -1)
)