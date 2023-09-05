INSERT INTO public.material(amount, title) VALUES (5, 'Keyboards');
INSERT INTO public.material(amount, title) VALUES (5, 'Mouses');

INSERT INTO public.applicationuser(email, firstname, isadmin, lastname, password) VALUES ('admin@user.ch', 'Jon', true, 'Landa', 'aupa123');
INSERT INTO public.applicationuser(email, firstname, isadmin, lastname, password) VALUES ('test@user.ch', 'Ohian', false, 'Sancet', 'OS8');

INSERT INTO public.booking(afternoon, date, halfday, morning, status, applicationuser_id) VALUES (false, '06-12-2006T10:00:00', true, true, false, 2);
INSERT INTO public.booking(afternoon, date, halfday, morning, status, applicationuser_id) VALUES (false, '06-12-2006T10:00:00', false, false, false, 2);