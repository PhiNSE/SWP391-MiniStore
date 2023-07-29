USE [MinistoreManagement]
GO
SET IDENTITY_INSERT [dbo].[role] ON 
GO
insert into [dbo].[role]([role_id],[name],[base_salary],[is_deleted]) values(1,'admin',null,0)
GO
insert into [dbo].[role]([role_id],[name],[base_salary],[is_deleted]) values(2,'saler',20.00,0)
GO
insert into [dbo].[role]([role_id],[name],[base_salary],[is_deleted]) values(3,'guard',15.00,0)
GO
SET IDENTITY_INSERT [dbo].[role] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_user] ON 
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(1,'admin','admin@gmail.com','Hello123','0931311835','111 test street',1,'false','2003-05-14',0,N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSr4APT4xaS2Boc5iD3b83qaBledYSWMxe9Fw&usqp=CAU')
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(2,'saler1','saler1@gmail.com','Hello123','0931311835','111 test street',2,'false','2003-05-14',0,N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDENjzTeAOG11Ay4uAG1RAPaKGuGX9sNA9Og&usqp=CAU')
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(3,'saler2','saler2@gmail.com','Hello123','0931311835','111 test street',2,'false','2003-05-14',0,N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDENjzTeAOG11Ay4uAG1RAPaKGuGX9sNA9Og&usqp=CAU')
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(4,'guard1','guard1@gmail.com','Hello123','0931311835','111 test street',3,'false','2003-05-14',1,N'https://cdn-icons-png.flaticon.com/512/1801/1801293.png')
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(5,'guard2','guard2@gmail.com','Hello123','0931311835','111 test street',3,'false','2003-05-14',1,N'https://cdn-icons-png.flaticon.com/512/1801/1801293.png')
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(6,'saler3','saler3@gmail.com','Hello123','0931311835','111 test street',2,'false','2003-05-14',0,N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDENjzTeAOG11Ay4uAG1RAPaKGuGX9sNA9Og&usqp=CAU')
GO
insert into [dbo].[tbl_user]([user_id],[name],[email],[password],[phone],[address],[role_id],[is_deleted],[dob],[gender],[user_img]) values(7,'guard3','guard3@gmail.com','Hello123','0931311835','111 test street',3,'false','2003-05-14',1,N'https://cdn-icons-png.flaticon.com/512/1801/1801293.png')
GO
SET IDENTITY_INSERT [dbo].[tbl_user] OFF
GO
insert into [dbo].[shift]([name],[start_work_hour],[end_work_hour],[coefficient]) values('saler-morning',6,12,1),
('saler-afternoon',12,18,1),
('saler-night',18,6,1.5),
('guard-day',6,18,1),
('guard-night',18,6,1.5)

insert into [dbo].[product_type]([name]) values(N'nước ngọt')

SET IDENTITY_INSERT [dbo].[product] ON 
GO
--INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (1, N'rau xao', 100, 1, CAST(2.20 AS Decimal(10, 2)), CAST(0.20 AS Decimal(10, 2)), N'cccccccccccccc', N'fdfsfdsfsdf', 1)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (22, N'6 lon nước ngọt Coca Cola 320ml', 23, 1, CAST(5860.00 AS Decimal(10, 2)), CAST(3860.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/125398/bhx/6-lon-nuoc-ngot-coca-cola-320ml-202303301632485062_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (23, N'6 lon soda Schweppes 320ml', 22, 1, CAST(4000.00 AS Decimal(10, 2)), CAST(2000.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/128857/bhx/6-lon-soda-schweppes-320ml-202303301629044011_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (24, N'6 chai nước ngọt Pepsi Cola 390ml', 21, 1, CAST(3800.00 AS Decimal(10, 2)), CAST(1800.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/83823/bhx/6-chai-nuoc-ngot-pepsi-cola-390ml-202212122325242919_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (25, N'6 lon sá xị Chương Dương Sleek 330ml', 21, 1, CAST(4800.00 AS Decimal(10, 2)), CAST(2800.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/196004/bhx/6-lon-sa-xi-chuong-duong-sleek-330ml-202212201606030258_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (26, N'6 lon nước ngọt 7 Up chanh 330ml', 22, 1, CAST(5860.00 AS Decimal(10, 2)), CAST(3860.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/198121/bhx/6-lon-nuoc-ngot-7-up-vi-chanh-320ml-202212201112261768_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (27, N'6 lon nước ngọt Sprite chanh 320ml', 27, 1, CAST(5600.00 AS Decimal(10, 2)), CAST(3600.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/195224/bhx/6-lon-nuoc-ngot-sprite-huong-chanh-320ml-202303302259217748_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (28, N'6 lon nước ngọt Mirinda soda kem 320ml', 22, 1, CAST(5800.00 AS Decimal(10, 2)), CAST(3800.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/195220/bhx/6-lon-nuoc-ngot-mirinda-vi-soda-kem-320ml-202212122338292664_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (29, N'2 chai nước ngọt Coca Cola Zero 600ml', 20, 1, CAST(1500.00 AS Decimal(10, 2)), CAST(-500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/307819/bhx/2-chai-nuoc-ngot-co-ga-coca-cola-zero-600ml-202305191506424529_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (30, N'2 chai Fanta soda kem trái cây 600ml', 20, 1, CAST(1500.00 AS Decimal(10, 2)), CAST(-500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/307816/bhx/2-chai-nuoc-ngot-co-ga-fanta-huong-soda-kem-trai-cay-600ml-202305191450342769_300x300.jpg', N'1231233312', NULL)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (31, N'2 chai nước ngọt Sprite chanh 600ml', 20, 1, CAST(1500.00 AS Decimal(10, 2)), CAST(-500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/307811/bhx/2-chai-nuoc-ngot-sprite-huong-chanh-600ml-202305191441173036_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (32, N'Thùng 24 lon nước ngọt Fanta soda kem 320ml', 22, 1, CAST(19600.00 AS Decimal(10, 2)), CAST(17600.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/143437/bhx/-202211261057088235_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (33, N'6 chai nước ngọt Pepsi Cola 600ml', 28, 1, CAST(5800.00 AS Decimal(10, 2)), CAST(3800.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/306894/bhx/6-chai-nuoc-ngot-pepsi-cola-chai-600ml-202305052254594952_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (34, N'6 lon nước ngọt Fanta xá xị 320ml', 23, 1, CAST(5600.00 AS Decimal(10, 2)), CAST(3600.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/195231/bhx/6-lon-nuoc-ngot-fanta-huong-xa-xi-320ml-202212011421008064_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (35, N'6 lon nước ngọt Fanta soda kem 320ml', 20, 1, CAST(5600.00 AS Decimal(10, 2)), CAST(3600.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/143436/bhx/6-lon-nuoc-ngot-fanta-vi-soda-kem-330ml-202212181654448976_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (36, N'6 lon nước ngọt Fanta nho 320ml', 20, 1, CAST(5600.00 AS Decimal(10, 2)), CAST(3600.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/282394/bhx/6-lon-nuoc-ngot-fanta-huong-nho-320ml-202210101550083419_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (37, N'6 lon nước ngọt Fanta cam 320ml', 23, 1, CAST(5600.00 AS Decimal(10, 2)), CAST(3600.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/193332/bhx/6-lon-nuoc-ngot-fanta-huong-cam-320ml-202210081539309685_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (38, N'6 chai nước ngọt Coca Cola Zero 600ml', 24, 1, CAST(4500.00 AS Decimal(10, 2)), CAST(2500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/231530/bhx/6-chai-nuoc-ngot-co-ga-coca-cola-zero-600ml-202210082143005250_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (39, N'6 chai nước ngọt Sprite chanh 600ml', 23, 1, CAST(4500.00 AS Decimal(10, 2)), CAST(2500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/203161/bhx/6-chai-nuoc-ngot-sprite-huong-chanh-600ml-202210081801263321_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (40, N'6 chai nước ngọt Fanta soda kem trái cây 600ml', 27, 1, CAST(4500.00 AS Decimal(10, 2)), CAST(2500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/219805/bhx/6-chai-nuoc-ngot-co-ga-fanta-huong-soda-kem-trai-cay-600ml-202210081614040076_300x300.jpg', N'1231233312', 0)
GO
INSERT [dbo].[product] ([product_id], [name], [quantity], [product_type_id], [price], [cost], [product_img], [product_code], [is_deleted]) VALUES (41, N'6 chai nước ngọt Coca Cola 600ml', 30, 1, CAST(4500.00 AS Decimal(10, 2)), CAST(2500.00 AS Decimal(10, 2)), N'https://cdn.tgdd.vn/Products/Images/2443/195226/bhx/6-chai-nuoc-ngot-coca-cola-600ml-202210081628531863_300x300.jpg', N'1231233312', 0)
GO
SET IDENTITY_INSERT [dbo].[product] OFF
GO
INSERT INTO [dbo].[holiday] ([holiday_date],[holiday_name]) VALUES
    ('2023-01-01', N'Tết Dương Lịch'),
    ('2023-01-02', N'Nghỉ Tết Dương Lịch'),
    ('2023-01-21', N'Tết Nguyên Đán'),
    ('2023-01-22', N'Tết Nguyên Đán'),
    ('2023-01-23', N'Tết Nguyên Đán'),
    ('2023-01-24', N'Tết Nguyên Đán'),
    ('2023-01-25', N'Tết Nguyên Đán'),
    ('2023-01-26', N'Tết Nguyên Đán'),
    ('2023-01-27', N'Tết Nguyên Đán'),
    ('2023-04-29', N'Giỗ Tổ Hùng Vương'),
    ('2023-04-30', N'Ngày Thống nhất đất nước'),
    ('2023-05-01', N'Ngày Quốc tế Lao động'),
    ('2023-05-01', N'Nghỉ Giỗ Tổ Hùng Vương'),
    ('2023-05-02', N'Nghỉ Ngày Thống nhất'),
    ('2023-09-02', N'Ngày Quốc khánh'),
    ('2023-09-04', N'Nghỉ Ngày Quốc khánh');
GO
insert into  [dbo].[ticket_type]([name]) values(N'Leave')
insert into  [dbo].[ticket_type]([name]) values(N'Cancle shift')

