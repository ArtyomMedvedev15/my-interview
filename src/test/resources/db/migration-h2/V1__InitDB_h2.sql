create table if not exists interview_session (
                                                 id bigint auto_increment primary key,
                                                 date_time timestamp,
                                                 candidate_name varchar(255)
    );

create table if not exists topic (
                                     id bigint auto_increment primary key,
                                     name varchar(255)
    );

create table if not exists question (
                                        id bigint auto_increment primary key,
                                        text varchar(255),
    expected_answer varchar(255),
    tags varchar(255),
    practical boolean not null,
    difficulty varchar(255),
    topic_id bigint,
    constraint fk_question_topic foreign key (topic_id) references topic
    );

create table if not exists question_attempt (
                                                id bigint auto_increment primary key,
                                                interview_session_id bigint,
                                                question_id bigint,
                                                candidate_answer varchar(255),
    notes varchar(255),
    result varchar(255),
    constraint fk_attempt_session foreign key (interview_session_id) references interview_session,
    constraint fk_attempt_question foreign key (question_id) references question
    );
