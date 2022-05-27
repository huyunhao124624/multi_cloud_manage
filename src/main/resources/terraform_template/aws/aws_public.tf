terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.9.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region = "us-east-1"
  access_key = "AKIAV75AJ4MXP3ZP3YEP"
  secret_key = "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"
}

resource "aws_security_group" "all" {
  name        = "allow_tls"
  description = "Allow TLS inbound traffic"
  vpc_id      = aws_vpc.multi_cloud_manage_aws_vpc.id

  ingress {
    description      = "TLS from VPC"
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "allow_all"
  }
}



resource "aws_vpc" "multi_cloud_manage_aws_vpc" {
  cidr_block = "172.16.0.0/16"

  tags = {
    Name = "multi_cloud_aws"
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.multi_cloud_manage_aws_vpc.id
}

resource "aws_subnet" "multi_cloud_manage_aws_subnet" {
  vpc_id            = aws_vpc.multi_cloud_manage_aws_vpc.id
  cidr_block        = "172.16.10.0/24"
  availability_zone = "us-east-1a"

  tags = {
    Name = "multi_cloud_aws"
  }
}

resource "aws_network_interface" "multi_cloud_manage_aws_network_interface" {
  subnet_id   = aws_subnet.multi_cloud_manage_aws_subnet.id
  // private_ips = ["172.16.10.100"]

  tags = {
    Name = "primary_network_interface"
  }
}

resource "aws_eip" "awsEipName" {
  instance = aws_instance.awsInstanceName.id
  vpc      = true
}

// resource "aws_eip_association" "awsEipAssociationName" {
//   instance_id   = aws_instance.awsInstanceName.id
//   allocation_id = aws_eip.awsEipName.id
// }

resource "aws_instance" "awsInstanceName" {
  ami           = "ami-005de95e8ff495156" # us-west-2
  instance_type = "t2.micro"

  network_interface {
    network_interface_id = aws_network_interface.multi_cloud_manage_aws_network_interface.id
    device_index         = 0
  }

  security_groups = [aws_security_group.all.id]

  // vpc_security_group_ids = [aws_security_group.all.id]

  // ebs_optimized = true

  #  credit_specification {
  #    cpu_credits = "unlimited"
  #  }

  ebs_block_device {
    device_name = " /dev/sdb"
    volume_size = "20"
  }

}

// resource "aws_network_interface_sg_attachment" "sg_attachment" {
//   security_group_id    = aws_security_group.all.id
//   network_interface_id = aws_instance.awsInstanceName.primary_network_interface_id
// }

// resource "aws_ebs_volume" "ebsExample" {
//   availability_zone = "us-east-1a"
//   size              = 40

//   tags = {
//     Name = "HelloWorld"
//   }
// }

// output "aws_public_ip" {
//   value = aws_eip_association.awsEipAssociationName.public_ip
// }

output "aws_public_ip" {
  value = aws_eip.awsEipName.public_ip
}



// data "aws_ec2_instance_type" "ait" {
//   instance_type = "t2.micro"
// }

// output "ait"{
//     value = data.aws_ec2_instance_type.ait
// }