USE [master]
GO
/****** Object:  Database [Inventory]    Script Date: 12/04/2023 12:13:25 ******/
CREATE DATABASE [Inventory]
GO
USE [Inventory]
GO
/****** Object:  Table [dbo].[CurrentStock]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CurrentStock](
	[ProductCode] [nvarchar](50) NOT NULL,
	[Quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CustomerCode] [nvarchar](50) NULL,
	[FullName] [nvarchar](100) NULL,
	[Address] [nvarchar](100) NULL,
	[Phone] [nvarchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[InputInfo]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InputInfo](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[SupplierCode] [nvarchar](50) NULL,
	[ProductCode] [nvarchar](50) NULL,
	[Quantity] [int] NOT NULL,
	[InputPrice] [float] NULL,
	[Date] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OutputInfo]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OutputInfo](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ProductCode] [nvarchar](50) NULL,
	[CustomerCode] [nvarchar](50) NULL,
	[Quantity] [int] NOT NULL,
	[OutputPrice] [float] NULL,
	[Date] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ProductCode] [nvarchar](50) NULL,
	[ProductName] [nvarchar](100) NULL,
	[Brand] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Supplier]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Supplier](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[SupplierCode] [nvarchar](50) NULL,
	[FullName] [nvarchar](100) NULL,
	[Address] [nvarchar](100) NULL,
	[Phone] [nvarchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 12/04/2023 12:13:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[FullName] [nvarchar](100) NULL,
	[UserName] [nvarchar](100) NULL,
	[Password] [nvarchar](max) NULL,
	[UserType] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[CurrentStock] ([ProductCode], [Quantity]) VALUES (N'SP01', 100)
INSERT [dbo].[CurrentStock] ([ProductCode], [Quantity]) VALUES (N'SP02', 15)
INSERT [dbo].[CurrentStock] ([ProductCode], [Quantity]) VALUES (N'SP03', 26)
INSERT [dbo].[CurrentStock] ([ProductCode], [Quantity]) VALUES (N'SP04', 15)
INSERT [dbo].[CurrentStock] ([ProductCode], [Quantity]) VALUES (N'SP05', 70)
GO
SET IDENTITY_INSERT [dbo].[Customer] ON 

INSERT [dbo].[Customer] ([Id], [CustomerCode], [FullName], [Address], [Phone]) VALUES (3, N'KH1', N'Loc ', N'VietNam', N'12221')
INSERT [dbo].[Customer] ([Id], [CustomerCode], [FullName], [Address], [Phone]) VALUES (4, N'VIP1', N'Admin', N'VietNam', N'00000')
INSERT [dbo].[Customer] ([Id], [CustomerCode], [FullName], [Address], [Phone]) VALUES (5, N'KH2', N'Hung', N'VietNam', N'12311')
INSERT [dbo].[Customer] ([Id], [CustomerCode], [FullName], [Address], [Phone]) VALUES (6, N'KH3', N'Thai', N'VietNam', N'12312')
SET IDENTITY_INSERT [dbo].[Customer] OFF
GO
SET IDENTITY_INSERT [dbo].[InputInfo] ON 

INSERT [dbo].[InputInfo] ([Id], [SupplierCode], [ProductCode], [Quantity], [InputPrice], [Date]) VALUES (22, N'Supp1', N'SP01', 150, 1000, NULL)
INSERT [dbo].[InputInfo] ([Id], [SupplierCode], [ProductCode], [Quantity], [InputPrice], [Date]) VALUES (23, N'Supp1', N'SP02', 15, 2000, NULL)
INSERT [dbo].[InputInfo] ([Id], [SupplierCode], [ProductCode], [Quantity], [InputPrice], [Date]) VALUES (24, N'Supp1', N'SP03', 36, 2500, NULL)
INSERT [dbo].[InputInfo] ([Id], [SupplierCode], [ProductCode], [Quantity], [InputPrice], [Date]) VALUES (25, N'Supp1', N'SP04', 20, 3000, NULL)
INSERT [dbo].[InputInfo] ([Id], [SupplierCode], [ProductCode], [Quantity], [InputPrice], [Date]) VALUES (26, N'Supp2', N'SP05', 70, 500, NULL)
SET IDENTITY_INSERT [dbo].[InputInfo] OFF
GO
SET IDENTITY_INSERT [dbo].[OutputInfo] ON 

INSERT [dbo].[OutputInfo] ([Id], [ProductCode], [CustomerCode], [Quantity], [OutputPrice], [Date]) VALUES (12, N'SP01', N'KH1', 50, 1200, NULL)
INSERT [dbo].[OutputInfo] ([Id], [ProductCode], [CustomerCode], [Quantity], [OutputPrice], [Date]) VALUES (13, N'SP03', N'KH2', 10, 3000, NULL)
INSERT [dbo].[OutputInfo] ([Id], [ProductCode], [CustomerCode], [Quantity], [OutputPrice], [Date]) VALUES (14, N'SP04', N'KH1', 5, 3500, NULL)
SET IDENTITY_INSERT [dbo].[OutputInfo] OFF
GO
SET IDENTITY_INSERT [dbo].[Product] ON 

INSERT [dbo].[Product] ([Id], [ProductCode], [ProductName], [Brand]) VALUES (36, N'SP01', N'Iphone 11', N'Apple')
INSERT [dbo].[Product] ([Id], [ProductCode], [ProductName], [Brand]) VALUES (37, N'SP02', N'Iphone 12', N'Apple')
INSERT [dbo].[Product] ([Id], [ProductCode], [ProductName], [Brand]) VALUES (38, N'SP03', N'Iphone 13', N'Apple')
INSERT [dbo].[Product] ([Id], [ProductCode], [ProductName], [Brand]) VALUES (39, N'SP04', N'Iphone 14', N'Apple')
INSERT [dbo].[Product] ([Id], [ProductCode], [ProductName], [Brand]) VALUES (40, N'SP05', N'Samsung A12', N'Samsung')
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
SET IDENTITY_INSERT [dbo].[Supplier] ON 

INSERT [dbo].[Supplier] ([Id], [SupplierCode], [FullName], [Address], [Phone]) VALUES (1, N'Supp1', N'Apple Store', N'US', N'11111111')
INSERT [dbo].[Supplier] ([Id], [SupplierCode], [FullName], [Address], [Phone]) VALUES (2, N'Supp2', N'Samsung Store', N'Korea', N'22222222')
INSERT [dbo].[Supplier] ([Id], [SupplierCode], [FullName], [Address], [Phone]) VALUES (3, N'Supp3', N'Xiaomi Store', N'China', N'33333333')
INSERT [dbo].[Supplier] ([Id], [SupplierCode], [FullName], [Address], [Phone]) VALUES (6, N'Supp4', N'FPT', N'VN', N'444444444')
INSERT [dbo].[Supplier] ([Id], [SupplierCode], [FullName], [Address], [Phone]) VALUES (9, N'Supp5', N'TGDD', N'VN', N'555554')
SET IDENTITY_INSERT [dbo].[Supplier] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([Id], [FullName], [UserName], [Password], [UserType]) VALUES (1, N'ADMIN', N'admin', N'admin', N'ADMIN')
INSERT [dbo].[Users] ([Id], [FullName], [UserName], [Password], [UserType]) VALUES (2, N'USER', N'user', N'user', N'USER')
INSERT [dbo].[Users] ([Id], [FullName], [UserName], [Password], [UserType]) VALUES (3, N'TEST', N'test', N'test', N'USER')
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Product__2F4E024F4E841F9A]    Script Date: 12/04/2023 12:13:26 ******/
ALTER TABLE [dbo].[Product] ADD UNIQUE NONCLUSTERED 
(
	[ProductCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[InputInfo] ADD  DEFAULT ((0)) FOR [InputPrice]
GO
ALTER TABLE [dbo].[OutputInfo] ADD  DEFAULT ((0)) FOR [OutputPrice]
GO
USE [master]
GO
ALTER DATABASE [Inventory] SET  READ_WRITE 
GO
