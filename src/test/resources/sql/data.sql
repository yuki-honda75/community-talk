insert into hobbies (name) values
('ゲーム'),
('スポーツ'),
('インドア全般'),
('アウトドア全般'),
('音楽'),
('ファッション'),
('グルメ'),
('勉強・資格'),
('DIY'),
('テレビ'),
('お笑い'),
('イラスト'),
('工作'),
('その他');

insert into categories (name, hobby_id) values
('J-POP', 5),
('FPS', 1),
('キャンプ', 4);

insert into communities (name, category_id) values 
('サンプル1', 1),
('サンプル2', 3),
('サンプル3', 2);