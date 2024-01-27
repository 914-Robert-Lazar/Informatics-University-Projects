(defun deleteEvenSublists (l n lin)
  (progn
    (print l)
  (cond
   ((null l) (progn
               (print n)
               (print lin)
               (cond
                ((and (= (mod n 2) 0) (= lin 1)) (progn
                                                   (print "xd")
                                                   nil
                                                   )
                 )
                (t l)
                )
               )
    )
   ((atom l) l)
   ((and (listp (car l)) (null (deleteEvenSublists (car l) 0 1))) (progn
                                                                    (print "k")
                                                                    (deleteEvenSublists (cdr l) (+ n 1) lin))
    )
   (t (cons (car l) (deleteEvenSublists (cdr l) (+ n 1) lin)))
   )
  )
)
