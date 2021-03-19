
CREATE TABLE message_entity (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   message_body VARCHAR(64) NOT NULL,
   message_channel VARCHAR(64) NOT NULL,
   message_recipient_name VARCHAR(64) NOT NULL,
   message_recipient_email VARCHAR(64) NOT NULL,
   message_recipient_phone_number VARCHAR(64) NOT NULL,
   message_recipient_phone_id VARCHAR(64) NOT NULL,
   message_schedule_date TIMESTAMP NOT NULL
);

CREATE TABLE chat_entity (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   chat_status VARCHAR(64) NOT NULL,
   chat_date TIMESTAMP NOT NULL,
   message_id BIGINT NOT NULL,
   FOREIGN KEY (message_id)
         REFERENCES message_entity (id)
);
