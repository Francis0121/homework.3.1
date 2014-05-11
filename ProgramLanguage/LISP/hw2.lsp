; 1.1 last-item
(defun last-item( lst )
	( cond
		( ( null (cdr lst) ) ( car lst ) )
		( t ( last-item ( cdr lst ) ) )
	)
)

; 1.2 remove-1st
(defun remove-1st( atm lst )
	(cond 
		(
			( null lst )
				nil
		)
		( 
			( not ( equal atm ( car lst ) ) ) 
				( cons 
					( car lst ) 
					( remove-1st atm (cdr lst) ) 
				) 
		)
		(	
			t 
				(cdr lst) 
		)
	)
)

; 1.3 append2
(defun append2 (x y)
	(cond 
		( (null (cdr x) ) (cons (car x) y) )
		( t (cons (car x) (append2 (cdr x) y) ) )
	)
)

; 1.4 merge2
(defun merge2 (x y)
	(cond
		( ( null x ) y )
		( ( null y ) x )
		(  
			(  <= (car x) (car y)  )   
			( cons (car x)  
				(  if (null (cdr x)) y (merge2 (cdr x) y ) ) 
			)
		)
		(  
			t
			( cons (car y) 
				( if  (null (cdr y)) x (merge2 x (cdr y) ) ) 
			)
		)
	)	
)


; 1.5 subst-all
(defun subst-all( atm1 atm2 lst )
	( cond
		( 
			( not ( null (car lst) ) )  
			( cons 
				( if ( equal ( car lst )  atm2 ) atm1 (car lst)  ) 
				( subst-all atm1 atm2 (cdr lst) ) 
			)    
		)
	)
)

; 2.1 count-all
(defun count-all( x )
	(cond 
		( 
			(null x) 
				0 
		)
		( 
			( atom ( car x ) ) 
				( 1+ ( count-all ( cdr x ) ) ) 
		)
		( 
			t 
				( +	
					( count-all (car x) ) 
					( count-all (cdr x) )  
				)
		)
	)
)

; 2.2 remove-all2 
(defun remove-all2 (  rx x )
	(cond
		( 
			( null x ) 
				nil 
		)
		( 
			(  equal  rx (car  x) )
				( if ( null (cdr x) ) nil ( remove-all2 rx (cdr x) ) ) 
		)
		(
			t
			( cons
				( if ( atom (car x) ) (car x) ( remove-all2 rx ( car x ) ) )
				( if ( null (cdr x) ) nil ( remove-all2 rx (cdr x) ) )
			)
		)
	)
)

; 2.3 reverse-all
(defun reverse-all (x)
	(cond 
		( 
			(atom x) 
				x 
		)
		( 
			(null (cdr x)) 
				x 
		)
		(	
			t 
			( append2 
				( reverse-all ( cdr x ) ) 
				( list ( reverse-all (car x ) ) )
			) 
		)
	)
)

; 2.4 depth
(defun depth (x)
	(cond 
		( 
			(atom x) 
				0 
		)
		(	
			t 
			( max 
				( 1+ ( depth (car x) ) ) 
				( depth (cdr x ) ) 
			) 
		)
	)
)

; 2.5 flatten
(defun flatten (x)
	(cond 
		( 
			(null x) 
				x 
		)
		( 
			( atom ( car x ) ) 
				( cons 
					( car x ) 
					( flatten ( cdr x ) )
				) 
		)
		( 
			t 
				( append2 
					( flatten ( car x ) ) 
					( flatten ( cdr x ) )
				) 
		)
	)
)

; 3.1 memberp
(defun memberp( lst1 lst2 )
	(cond
		( 
			(null lst2) nil 
		)
		( 
			(equal lst1 (car lst2)) t
		)
		( 
			t ( memberp lst1 (cdr lst2) )
		)
	)
)

; 3.2 union
(defun union2(lst1 lst2)
	(cond
		(
			( null lst1) 
				lst2
		)
		(
			( memberp ( car lst1 ) lst2 )
				( union2 (cdr lst1) lst2 )
		)
		(
			t
				(cons
					( car lst1 )
					( union2 (cdr lst1) lst2 )
				)
		)
	)
)

; 3.3 set-diff
(defun set-diff(lst1 lst2)
	(cond
		(
			(null lst1)
				nil
		)
		(
			(null lst2)
				lst1
		)
		(
			(not ( memberp (car lst2) lst1) )
				( set-diff lst1 (cdr lst2) )
		)
		(
			t 
			( set-diff
				( remove-1st (car lst2) lst1 )
				( cdr lst2 )
			)
		)
	)
)

; 3.4 subsetp
(defun subsetp2(lst1 lst2)
	(cond
		(
			(null lst1)
				t
		)
		(
			(memberp (car lst1) lst2)
				( subsetp2 (cdr lst1) lst2 )
		)
		(
			t
				nil
		)
	)
)

; 3.5 comp
(defun comp(lst1 lst2)
	(cond
		(
			(null lst1)
				nil
		)
		(
			(null lst2)
				lst1
		)
		(
			(not ( memberp (car lst2) lst1) )
				( comp lst1 (cdr lst2) )
		)
		(
			t 
			( comp
				( remove-all2 (car lst2) lst1 )
				( cdr lst2 )
			)
		)
	)
)

;4
(defun infer( lst1 lst2)
	(cond
		(
			( equal  (car lst2) lst1 ) 
				(cdr lst2) 
		)
		(
			(atom lst1)
				lst1
		)
		(
			( memberp (car lst2) lst1 )
				( remove-all2
					nil
					( flatten 
						(cons 
							( infer (car lst1) lst2 )
							( infer 
								(cdr lst1)
								( if (memberp (car lst2) (cdr lst1) )
									lst2 
									nil
								)
							)
						)
					)
				)
		)
		(
			(null lst2)
				lst1
		)
	)
)

;5
(defun distance(a b)
	(cond
		(
			(null (car a) )
				0
		)
		(
			t
			( + 
				( if ( not (eq (car a) (car b ) ) ) 1 0 )
				(
					distance (cdr a) (cdr b)
				)
			)	
		)
	)
)

(defun closest( lst )	
	(cond
		(
			( null (cddr lst) )
				( list 
					(car lst) (cadr lst)
				)
		)
		(
			( < 
				( distance ( car (calc-closest  lst  ) )
						( cadr (calc-closest  lst  ) ) 
				)
				( distance ( car (calc-closest (cdr lst) ) )
						( cadr (calc-closest (cdr lst) ) )
				)
			)
				
				( cond
					(
						( equal ( cadr (calc-closest lst) ) (cadr lst ) )
							( closest 
								( append2 
									( remove-1st (cadr lst) lst ) 
									( list ( cadr lst ) )
								)
							)
					)
					(
						t
						( closest ( remove-1st (cadr lst) lst ) ) 
					)
				)
		)
		(
			t
				( closest ( remove-1st (car lst) lst ) )
		)
		
	)
)

(defun calc-closest(lst)
	(cond
		(
			( null (cddr lst) )
				(list 
					(car lst) (cadr lst)
				)
		)
		(
			( <
					( distance (car lst) (cadr lst) )
					( distance (car lst) (caddr lst ) )
			)
				(calc-closest (remove-1st (caddr lst) lst) )
		)
		(
			t
				( calc-closest (remove-1st (cadr lst) lst ) )
		)
	)
)
