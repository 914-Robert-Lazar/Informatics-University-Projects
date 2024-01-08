;As an example, the tree
;    A
;  / | \
;  B C  D
;   / \  \
;   E  F  G
; / | \
;H  I  J
;      |
;      K
;is represented as follows:
;(A 2 B 0 C 2 D 0 E 0) (1)

;5. Return the level (depth) of a node in a tree of type (1). The level of the root element is 0.


(defun depthOfNode (tree node depthList)
  (cond
   ((and (not (null depthList)) (= 0 (car depthList))) 
    (progn
      (setf (cadr depthList) (- (cadr depthList) 1))
      (depthOfNode tree node (cdr depthList))
      )
    )
   ((not (numberp (car tree))) (cond
                                ((eq (car tree) node) (list-length depthList))
                                (t (depthOfNode (cdr tree) node depthList))
                                )
    )                    
   ((numberp (car tree)) (progn
                           (setq depthList (cons (car tree) depthList))
                           (depthOfNode (cdr tree) node depthList)
                           ))
   )
)
