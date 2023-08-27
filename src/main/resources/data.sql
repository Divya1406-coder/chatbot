CREATE TABLE FAQ (
	id LONG AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(1000) NOT NULL
);

INSERT INTO FAQ (question, answer) VALUES
    ('Yes', 'Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We''ll see you there!'),
    ('No', 'Ok, thanks. Can you let me know why not? Respond 1: Too far Respond 2: Not available Respond 3: Other'),
    ('1', 'Thanks for letting me know. I''ll avoid offering shifts at this location in the future'),
    ('2', 'Thanks for letting me know. I''ll avoid offering shifts at this time in the future'),
    ('3', 'Ok. Thanks. I won''t offer shifts at this location or time in the future');
