/*******************************************************************************
* * Copyright 2018 Impetus Infotech.
* *
* * Licensed under the Apache License, Version 2.0 (the "License");
* * you may not use this file except in compliance with the License.
* * You may obtain a copy of the License at
* *
* * http://www.apache.org/licenses/LICENSE-2.0
* *
* * Unless required by applicable law or agreed to in writing, software
* * distributed under the License is distributed on an "AS IS" BASIS,
* * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* * See the License for the specific language governing permissions and
* * limitations under the License.
******************************************************************************/
package com.impetus.fabric.spark.connector.rdd

import com.impetus.blkch.spark.connector.util.ConfigParam
import org.apache.spark.SparkConf

case class WriteConf(chaincode: String, function: String)

object WriteConf {
  
  val ChaincodeName = ConfigParam[String]("spark.fabric.chaincode.name",
    null,
    """
      | Specify the name of the chaincode used to insert data 
      | in dataframe into Hyperledger fabric world state.
    """.stripMargin)
    
  val ChaincodeFunction = ConfigParam[String]("spark.fabric.chaincode.function",
      null,
      """
        | Specify the name of the chaincode's function used to insert data 
        | in dataframe into Hyperledger fabric world state.
      """.stripMargin)
      
  val Properties = Set(
    ChaincodeName,
    ChaincodeFunction
  )
  
  def apply(conf: SparkConf): WriteConf = apply(conf, Map[String, String]())
  
  def apply(conf: SparkConf, options: Map[String, String]): WriteConf = {
    require(conf.getOption(ChaincodeName.name).isDefined || options.contains(ChaincodeName.name),
      s"""
        | Configuration parameter ${ChaincodeName.name} should be set
      """.stripMargin)
    require(conf.getOption(ChaincodeFunction.name).isDefined || options.contains(ChaincodeFunction.name),
      s"""
        | Configuration parameter ${ChaincodeFunction.name} should be set
      """.stripMargin)
    WriteConf(
      chaincode = conf.get(ChaincodeName.name, options.getOrElse(ChaincodeName.name, ChaincodeName.default)),
      function = conf.get(ChaincodeFunction.name, options.getOrElse(ChaincodeFunction.name, ChaincodeFunction.default))
    )
  }
}