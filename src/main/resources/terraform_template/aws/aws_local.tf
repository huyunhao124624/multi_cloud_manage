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
  access_key = "test"
  secret_key = "test"

  endpoints {
    apigateway     = "http://localhost:4566"
    apigatewayv2   = "http://localhost:4566"
    cloudformation = "http://localhost:4566"
    cloudwatch     = "http://localhost:4566"
    dynamodb       = "http://localhost:4566"
    ec2            = "http://localhost:4566"
    es             = "http://localhost:4566"
    elasticache    = "http://localhost:4566"
    firehose       = "http://localhost:4566"
    iam            = "http://localhost:4566"
    kinesis        = "http://localhost:4566"
    lambda         = "http://localhost:4566"
    rds            = "http://localhost:4566"
    redshift       = "http://localhost:4566"
    route53        = "http://localhost:4566"
    s3             = "http://s3.localhost.localstack.cloud:4566"
    secretsmanager = "http://localhost:4566"
    ses            = "http://localhost:4566"
    sns            = "http://localhost:4566"
    sqs            = "http://localhost:4566"
    ssm            = "http://localhost:4566"
    stepfunctions  = "http://localhost:4566"
    sts            = "http://localhost:4566"
  }
}

resource "aws_vpc" "multi_cloud_manage_aws_vpc" {
  cidr_block = "172.16.0.0/16"

  tags = {
    Name = "multi_cloud_aws"
  }
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
  private_ips = ["172.16.10.100"]

  tags = {
    Name = "primary_network_interface"
  }
}

resource "aws_eip" "${awsEipName}" {
  instance = aws_instance.${awsInstanceName}.id
  vpc      = true
}

resource "aws_eip_association" "${awsEipAssociationName}" {
  instance_id   = aws_instance.${awsInstanceName}.id
  allocation_id = aws_eip.${awsEipName}.id
}

resource "aws_instance" "${awsInstanceName}" {
  ami           = "${amiOutId}" # us-west-2
  instance_type = "${instanceType}"

  network_interface {
    network_interface_id = aws_network_interface.multi_cloud_manage_aws_network_interface.id
    device_index         = 0
  }

#  credit_specification {
#    cpu_credits = "unlimited"
#  }

  ebs_block_device {
    device_name = "${diskName}"
    volume_size = "${diskSize}"
  }

}

output "aws_public_ip" {
  value = aws_eip_association.${awsEipAssociationName}.public_ip
}


// data "aws_ec2_instance_type" "ait" {
//   instance_type = "t2.micro"
// }

// output "ait"{
//     value = data.aws_ec2_instance_type.ait
// }