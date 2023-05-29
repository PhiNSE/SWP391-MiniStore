DROP DATABASE [MinistoreManagement]
USE [master]
GO
/****** Object:  Database [MinistoreManagement]    Script Date: 15/05/2023 07:02:12 ******/
CREATE DATABASE [MinistoreManagement]

USE [MinistoreManagement]
/****** Object:  Table [dbo].[order]    Script Date: 15/05/2023 07:02:12 ******/
CREATE TABLE [dbo].[user_notification](
	[user_notification_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[notification_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[user_notification_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notification](
	[notification_id] [int] IDENTITY(1,1) NOT NULL,
	[description] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[notification_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tbl_order](
	[order_id] [int] IDENTITY(1,1) NOT NULL,
	[type] [bit] NOT NULL,
	[user_id] [int] NOT NULL,
	[date] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[order_detail_id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NOT NULL,
	[order_id] [int] NOT NULL,
	[quantity] [int]  NULL,
	[price] [decimal](10, 2)  NULL,
	[total] [decimal](10, 2)  NULL,
	[product_voucher_id] [int]  NULL,
	
 CONSTRAINT [PK__orderDet__3213E83FEAB6E822] PRIMARY KEY CLUSTERED 
(
	[order_detail_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payslip]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payslip](
	[payslip_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[start_date] [date] NULL,
	[end_date] [date] NULL,
	[shift_count] int NULL,
	[salary] [decimal](10, 2) NULL
PRIMARY KEY CLUSTERED 
(
	[payslip_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[product_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [Nvarchar](255)  NULL,
	[quantity] [int]  NULL,
	[product_type_id] [int] NOT NULL,
	[price] [decimal](10, 2)  NULL,
	[cost] [decimal](10, 2)  NULL,
	[product_img] [text] NULL,
	[product_code] [Nvarchar](255) NULL,
	[is_deleted] [bit] NULL ,
PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

/****** Object:  Table [dbo].[productType]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_type](
	[product_type_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [Nvarchar](255)  NULL,
PRIMARY KEY CLUSTERED 
(
	[product_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[
]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_voucher](
	[product_voucher_id] [int] IDENTITY(1,1) NOT NULL,
	[voucher_id] [int] NOT NULL,
	[product_id] [int]  NULL,
	
PRIMARY KEY CLUSTERED 
(
	[product_voucher_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[role_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [Nvarchar](255)  NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shift]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shift](
	[shift_id] [int] IDENTITY(1,1) NOT NULL,
	[start_work_hour] [decimal](5, 3)  NULL,
	[end_work_hour] [decimal](5, 3)  NULL,
	[coefficient] [int]  NULL,
	[is_deleted] [bit] NULL ,
PRIMARY KEY CLUSTERED 
(
	[shift_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_user](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [Nvarchar](255)  NULL,
	[email] [Nvarchar](255)  NULL,
	[password] [Nvarchar](255)  NULL,
	[phone] [Nvarchar](255) NULL,
	[address] [Nvarchar](255)  NULL,
	[role_id] [int] NOT NULL,
	[is_deleted] [bit] NULL ,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_shift]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_shift](
	[user_shift_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[shift_id] [int] NOT NULL,
	[work_date] [date]  NULL,
	[is_holiday] [bit]  NULL,
	[is_weekend] [bit]  NULL,
	[is_checked_in] [bit]  NULL,
	[is_checked_out] [bit]  NULL,
 CONSTRAINT [PK__schedule__3213E83F6C24A4B0] PRIMARY KEY CLUSTERED 
(
	[user_shift_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[voucher]    Script Date: 15/05/2023 07:02:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[voucher](
	[voucher_id] [int] IDENTITY(1,1) NOT NULL,
	[description] [Nvarchar](max)  NULL,
	[quantity] [int] NULL,
	[min_item] [int] NULL,
	[min_total] [int] NULL,
	[max_percent] DECIMAL(5, 2) NULL,
	[percent_discount] DECIMAL(5, 2) NULL,
	[expired_date] [date]  NULL,
	[is_deleted] [bit] NULL ,
PRIMARY KEY CLUSTERED 
(
	[voucher_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[user_notification]  WITH CHECK ADD  CONSTRAINT [fk39] FOREIGN KEY([notification_id])
REFERENCES [dbo].[notification] ([notification_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_notification] CHECK CONSTRAINT [fk39]
GO
ALTER TABLE [dbo].[user_notification]  WITH CHECK ADD  CONSTRAINT [fk19] FOREIGN KEY([user_id])
REFERENCES [dbo].[tbl_user] ([user_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_notification] CHECK CONSTRAINT [fk19]
GO
ALTER TABLE [dbo].[tbl_order]  WITH CHECK ADD  CONSTRAINT [fk10] FOREIGN KEY([user_id])
REFERENCES [dbo].[tbl_user] ([user_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[tbl_order] CHECK CONSTRAINT [fk10]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [fk2] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [fk2]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [fk21] FOREIGN KEY([product_voucher_id])
REFERENCES [dbo].[product_voucher] ([product_voucher_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [fk21]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [fk3] FOREIGN KEY([order_id])
REFERENCES [dbo].[tbl_order] ([order_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [fk3]
GO
ALTER TABLE [dbo].[payslip]  WITH CHECK ADD  CONSTRAINT [fk40] FOREIGN KEY([user_id])
REFERENCES [dbo].[tbl_user] ([user_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[payslip] CHECK CONSTRAINT [fk40]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [fk1] FOREIGN KEY([product_type_id])
REFERENCES [dbo].[product_type] ([product_type_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [fk1]

ALTER TABLE [dbo].[product_voucher]  WITH CHECK ADD  CONSTRAINT [fk15] FOREIGN KEY([voucher_id])
REFERENCES [dbo].[voucher] ([voucher_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_voucher] CHECK CONSTRAINT [fk15]
GO
ALTER TABLE [dbo].[product_voucher]  WITH CHECK ADD  CONSTRAINT [fk20] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id]) 
GO
ALTER TABLE [dbo].[product_voucher] CHECK CONSTRAINT [fk20]
GO
ALTER TABLE [dbo].[tbl_user]  WITH CHECK ADD  CONSTRAINT [fk4] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([role_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[tbl_user] CHECK CONSTRAINT [fk4]
GO
ALTER TABLE [dbo].[user_shift]  WITH CHECK ADD  CONSTRAINT [fk5] FOREIGN KEY([user_id])
REFERENCES [dbo].[tbl_user] ([user_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_shift] CHECK CONSTRAINT [fk5]
GO
ALTER TABLE [dbo].[user_shift]  WITH CHECK ADD  CONSTRAINT [fk6] FOREIGN KEY([shift_id])
REFERENCES [dbo].[shift] ([shift_id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_shift] CHECK CONSTRAINT [fk6]
GO
USE [master]
GO
ALTER DATABASE [MinistoreManagement] SET  READ_WRITE 
GO