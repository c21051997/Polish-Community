-- mock data for category
delete from information;
delete from categories;
INSERT INTO categories (category_title, category_description, user_id)
VALUES
    ('Work', 'Information about employment opportunities, workplace rights, and support available to migrants in Wales', 1),
    ('Housing', 'Guidance on finding accommodation, understanding tenancy agreements, and accessing housing support services', 2),
    ('Health and Social care', 'Information on accessing healthcare services, registering with a GP, and support for social care needs', 3),
    ('Social connections', 'Opportunities and programs to foster integration and build friendships between migrants and local communities', 4),
    ('Education and Skills', 'Details about schools, colleges, training programs, and opportunities for skill development in Wales', 5),
    ('Safety and Stability', 'Important information on maintaining personal safety, reporting hate crimes, and accessing support for victims of crime', 6),
    ('Rights and Responsibilities', 'Guidance on legal rights, civic responsibilities, and how migrants can actively participate in Welsh society', 7);

-- mock data for event

delete from event;

-- mock data for news
delete from news;
INSERT INTO news (news_title, news_summary, news_source, news_link, news_image_url, user_id, news_upload_date)
VALUES
    ('Poland Will Not Support EU Mercosur Free Trade Deal in Current Form',
     'Poland has announced it will not support the EU-Mercosur free trade agreement in its current form, joining France in opposition due to concerns over agriculture. Polish farmers fear unfair competition from cheaper South American products. The deal, initially agreed in 2019, faces resistance from various EU countries, particularly over transparency and sustainability issues. Poland and France argue that the deal could harm European farmers, especially in beef, poultry, and sugar sectors, due to differing production standards in Mercosur countries.',
     'Notes from Poland',
--      'http://localhost:8080/category', -- To test invalid url
     'https://notesfrompoland.com/2024/11/26/poland-will-not-support-eu-mercosur-free-trade-deal-in-current-form/',
     'assets/polish-farmers.jpg',
     1, current_date),
    ('Poland and Ukraine Release Joint Statement on Exhumation of WWII Massacre Victims',
     'Poland and Ukraine have issued a joint statement to continue efforts to uncover the truth behind the WWII massacres...',
     'Notes from Poland',
     'https://notesfrompoland.com/2024/11/26/poland-and-ukraine-release-joint-statement-on-exhumation-of-wwii-massacre-victims/',
     '',
     2, current_date),
    ('Poland Beats Germany in Euro 2024 Qualifiers',
     'Poland secured a 2-1 victory against Germany in the UEFA Euro 2024 qualifiers...',
     'TVN24',
     'https://notesfrompoland.com/2024/11/27/constitutional-court-rejects-polish-governments-change-to-teaching-of-religion-in-schools/',
     '',
     3, current_date),

    ('Record Attendance at Krakow Book Fair',
     'The Krakow Book Fair saw over 80,000 visitors, setting a new record for the event...',
     'Dziennik Polski',
     'https://notesfrompoland.com/2024/11/25/nineteenth-century-japanese-coin-found-buried-in-poland/',
     '',
     4, current_date),

    ('Poland Increases Investment in Wind Farms for Green Energy Goals',
     'Poland is committing billions in investments to boost renewable energy capacity and meet climate targets by 2030...',
     'Polsat News',
     'https://notesfrompoland.com/2024/11/25/struggling-battery-maker-northvolt-to-shut-down-energy-storage-manufacturing-in-poland/',
     '',
     5, current_date);



-- insert standard roles into roles table
INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('USER');

-- insert a admin user into the table
INSERT INTO users (email, password, fullname, dob, role_id, organization) VALUES
    ('admin@gmail.com', 'Password!', 'Harri Daives', '2003-07-22', 1, 'Ludek Polonia Wajiska'),
    ('user1@gmail.com', 'Password123', 'John Smith', '1998-04-15', 2, 'Tech Solutions Ltd'),
    ('user2@gmail.com', 'SecurePass1!', 'Jane Doe', '1995-11-20', 2, 'Global Innovations'),
    ('user3@gmail.com', 'Pa$$w0rd', 'Emily Johnson', '1990-02-10', 2, 'Quick Logistics'),
    ('user4@gmail.com', 'MySecureP@ss', 'Michael Brown', '1988-06-30', 2, 'Creative Works Inc.'),
    ('user5@gmail.com', 'Welcome123!', 'Jessica Davis', '2001-08-12', 2, 'EduTech Solutions'),
    ('user6@gmail.com', 'P@ssword789', 'Daniel Garcia', '1993-03-05', 2, 'HealthCare Inc.'),
    ('user7@gmail.com', 'SuperSecure@', 'Sophia Wilson', '1999-09-25', 2, 'Food Supply Co.'),
    ('user8@gmail.com', 'Passw0rd!', 'James Martinez', '2000-01-18', 2, 'Tech Support Hub'),
    ('user9@gmail.com', 'UserPass!234', 'Isabella Taylor', '1996-05-22', 2, 'Dynamic Solutions'),
    ('user10@gmail.com', 'ChangeMe#1', 'David Anderson', '1992-12-13', 2, 'EcoGreen Enterprises');


INSERT INTO users (email, password, fullname, dob, role_id)
VALUES ('user@email.com', 'Abcdef1!', 'Jane Doe', '2000-01-01', 1 );
UPDATE user_profile SET bio = 'Jane''s bio' WHERE user_id = (SELECT id from users where email = 'user@email.com');

-- insert the user and role id's of the created roles and user
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);

-- Insert posts
INSERT INTO feed (post_image_url, post_title, post_description, post_time, user_id) VALUES
    ('uploads/36b2c38d-c9d5-4e14-a433-895a565d3abf_steve-johnson-D7AuHpLxLPA-unsplash.jpg', 'Post 1', 'Description for post 1', '2024-12-07', 1),
    ('uploads/5720f047-a3ca-4d4e-ab20-343aae7cc485_premium_photo-1733514691627-e62171fc052c.avif', 'Post 2', 'Description for post 2', '2024-12-07', 2),
    ('uploads/d0753820-30b3-429c-92c4-d82c910ba083_nicolas-jehly-0UU9-_1EMvM-unsplash.jpg', 'Post 3', 'Description for post 3', '2024-12-07', 3);

-- Insert tags
INSERT INTO tags (tag_name) VALUES
    ('Technology'),
    ('Lifestyle'),
    ('Health'),
    ('Sports'),
    ('News');

-- Insert post-tag relationships
INSERT INTO feed_tags (post_id, tag_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4);

-- Insert likes
INSERT INTO post_likes (post_id, user_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 2),
    (2, 4);

insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url,whyJoin,benefits)
values ('Science Fair', 'Students explore through the science fair', 'Cardiff', current_date,current_time, 1, 'https://marketplace.canva.com/EAE53TNAVD8/1/0/1131w/canva-event-present-science-fair-promotion-poster-1abqT-GiCNQ.jpg','Participating in the Science Fair offers you a unique opportunity to dive deep into the world of science, innovation, and discovery. Whether you''re a student eager to showcase your scientific knowledge or an individual with a passion for learning, this event is the perfect platform to fuel your curiosity. Here’s why you should join:
Engage with Innovative Ideas: Explore cutting-edge scientific projects that challenge the status quo and inspire new ways of thinking.
Collaborate with Like-Minded Individuals: Meet fellow science enthusiasts, students, and professionals who share your interests and passion for discovery.
Boost Your Critical Thinking: Through the process of research, experimentation, and presentation, you’ll develop critical problem-solving skills that are invaluable in any field.
Expand Your Knowledge: Learn about new technologies, scientific theories, and groundbreaking advancements that will shape the future of science and innovation.
Be a Part of a Larger Community: Join a global community of science advocates and future scientists, making valuable connections that could open doors to future opportunities.', 'Hands-On Experience: Gain practical, hands-on experience in the scientific method, from research and hypothesis testing to data analysis and presentation.
Develop Presentation Skills: Sharpen your ability to communicate complex scientific concepts in an engaging and accessible way, an essential skill for any future career.
Exposure to Career Opportunities: Connect with professionals in science, education, and industry, opening up potential career pathways, internships, and scholarships.
Recognition and Prizes: Stand a chance to win awards and gain recognition for your hard work and creativity. Whether you’re awarded for your innovation, research, or presentation, your efforts will be acknowledged.
Confidence Building: Presenting your work to peers, teachers, and judges builds confidence in your abilities and boosts self-esteem, allowing you to grow as both a scientist and an individual.
Inspire Future Projects: Your participation could spark new ideas and motivate others to start their own scientific endeavors, contributing to a culture of curiosity and learning.
Stay Ahead of the Curve: By participating, you gain knowledge about the latest trends in science and technology, giving you an edge in academic and professional fields.
By joining this science fair, you are not only enriching your own learning experience but also contributing to a vibrant community of innovators and explorers.');
insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url,whyJoin,benefits)
values ('Games Fair', 'Gamers explore through the game fair', 'Bristol', current_date,current_time, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/game-event-poster-template-c54aaeed440befaacf79e6dd35deb8f5_screen.jpg?ts=1486132851','Abc', 'Def');
insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url,whyJoin,benefits)
values ('Bikes Fair', 'Riders explore through the Ride fair', 'Newport', current_date,current_time, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/bike-fest-poster-design-template-fb1cc1ab4b2aee783f8ee75476c4c92d_screen.jpg?ts=1637012682','Abc', 'Def');
insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url)
values ('Bikes Fair', 'Riders explore through the Ride fair', 'Newport', current_date,current_time, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/bike-fest-poster-design-template-fb1cc1ab4b2aee783f8ee75476c4c92d_screen.jpg?ts=1637012682');
insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url)
values ('Bikes Fair', 'Riders explore through the Ride fair', 'Newport', current_date,current_time, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/bike-fest-poster-design-template-fb1cc1ab4b2aee783f8ee75476c4c92d_screen.jpg?ts=1637012682');
insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url)
values ('Bikes Fair', 'Riders explore through the Ride fair', 'Newport', current_date,current_time, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/bike-fest-poster-design-template-fb1cc1ab4b2aee783f8ee75476c4c92d_screen.jpg?ts=1637012682');
insert into event (event_title, event_description, location, event_date, event_time,user_id, event_poster_url)
values ('Bikes Fair', 'Riders explore through the Ride fair', 'Newport', current_date,current_time, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/bike-fest-poster-design-template-fb1cc1ab4b2aee783f8ee75476c4c92d_screen.jpg?ts=');
