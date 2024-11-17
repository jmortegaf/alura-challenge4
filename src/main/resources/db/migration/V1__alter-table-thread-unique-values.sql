alter table thread add constraint unique_title unique(title);
alter table thread add constraint unique_message unique(message);
