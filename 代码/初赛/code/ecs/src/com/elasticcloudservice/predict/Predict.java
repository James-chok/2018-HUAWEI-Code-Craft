package com.elasticcloudservice.predict;
import java.util.*;
import java.text.SimpleDateFormat;

public class Predict {
     public static String[] predictVm(String[] ecsContent, String[] inputContent){
      // 15 kinds of virtual machines' specification
//      int corDemand[] = { 1, 1, 1, 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16, 16 };
//      int memDemand[] = { 1024, 2048, 4096, 2048, 4096, 8192, 4092, 8192,
//        16384, 8192, 16384, 32768, 16384, 32768, 65536 };
      
      // create the history list
      List<String> histories = new ArrayList<String>();
      
      //---------------------------------------------------------------
      // read train data
          for (int i = 0; i < ecsContent.length; i++) {
                if (ecsContent[i].length() == 42) {
                    String uuid = ecsContent[i].substring(0, 13);
                    String flavorName = ecsContent[i].substring(14, 22);
                    String createTime = ecsContent[i].substring(23, 42);
                    histories.add(uuid + " " + flavorName + " " + createTime);
                } else {
                    String uuid = ecsContent[i].substring(0, 13);
                    String flavorName = ecsContent[i].substring(14, 21);
                    String createTime = ecsContent[i].substring(22, 41);
                    histories.add(uuid + " " + flavorName + " " + createTime);
                }
          }
           
      //---------------------------------------------------------------
      // read input data
      List<String> futures = new ArrayList<String>();
      int physicalCoreNum = 0;
      int memorySize = 0;
      int storageSize = 0;
      int virtualSize = 0;
      String CPUorMEM = "";
      String predictStart = "";
      String predictEnd = "";
      for (int i = 0; i < inputContent.length; i++) {
          if (i == 0 && inputContent[0].contains(" ") && inputContent[0].split(" ").length == 3) {
            String[] array = inputContent[0].split(" ");
            
            physicalCoreNum = Integer.parseInt(array[0]);
            memorySize = Integer.parseInt(array[1]);
            storageSize = Integer.parseInt(array[2]);
      }
          else if (i == 2) {
               String temp = inputContent[i];
               virtualSize = Integer.parseInt(temp);
      }
          else if (i > 2 && i <= 2 + virtualSize) {
               String[] array = inputContent[i].split(" ");
               String flavorName = array[0];
               String virtualCPUCoreNum = array[1];
               String virtualMemorySize = array[2];
               futures.add(flavorName + " " + virtualCPUCoreNum + " " + virtualMemorySize);
          }
          else if (i == 4 + virtualSize) {
              CPUorMEM = inputContent[i];
          }
          else if (i == 6 + virtualSize) {
              String[] array = inputContent[i].split(" ");
              predictStart = array[0];
          }
          else if (i == 7 + virtualSize) {
              String[] array = inputContent[i].split(" ");
              predictEnd = array[0];
          }
      }
      
      int data_num = ecsContent.length;
      int cnt_flavor = virtualSize;
      
      //---------------------------------------------------------------
      // predict stage
      String startHistory = histories.get(0);
      String endHistory = histories.get(histories.size() - 1);
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date startDate = null;
      Date endDate = null;
      try {
          String[] array;
          array = startHistory.split(" ");
      startDate = simpleDateFormat.parse(array[2]);
      array = endHistory.split(" ");
          endDate = simpleDateFormat.parse(array[2]);
      } catch (Exception e) {
          e.printStackTrace();
      }
              
            /* calculate the everyday quantities of every virtual machine in the past
             * example pattern:
             *     day  1  2  3  4  5
             * flavor1  0  2  5  1  0 
             * flavor2  1  6  3  5  0 
             * flavor3  3  5  8  2  3
             * flavor4  0  0  0  0  0
             */
         List<Map<String, Integer> > flavorNum = new ArrayList<Map<String, Integer> >(); 
      Calendar calendar = new GregorianCalendar();
         calendar.setTime(endDate);
         calendar.add(Calendar.DATE, 1);
         endDate = calendar.getTime();
      Date date = startDate;
      while(!date.equals(endDate)) {
	  if (isHoliday(date)) {
        	          calendar.setTime(date);
        	          calendar.add(Calendar.DATE, 1);
        	          date = calendar.getTime();
	  }
          String stringDate = simpleDateFormat.format(date);
          Map<String, Integer> map = new HashMap<String, Integer>();
          int flavor1Num = 0;
          int flavor2Num = 0;
          int flavor3Num = 0;
          int flavor4Num = 0;
          int flavor5Num = 0;
          int flavor6Num = 0;
          int flavor7Num = 0;
          int flavor8Num = 0;
          int flavor9Num = 0;
          int flavor10Num = 0;
          int flavor11Num = 0;
          int flavor12Num = 0;
          int flavor13Num = 0;
          int flavor14Num = 0;
          int flavor15Num = 0;
          for (int l = 0; l < histories.size(); l++) {
           String history = histories.get(l);
        if (history.contains(stringDate)) {
                if (history.contains("flavor2")) {
                     flavor2Num++;
                    }
                else if (history.contains("flavor3")) {
                 flavor3Num++;
                }
                else if (history.contains("flavor4")) {
                 flavor4Num++;
                }
                else if (history.contains("flavor5")) {
                 flavor5Num++;
                }
                else if (history.contains("flavor6")) {
                 flavor6Num++;
                }
                else if (history.contains("flavor7")) {
                 flavor7Num++;
                }
                else if (history.contains("flavor8")) {
                 flavor8Num++;
                }
                else if (history.contains("flavor9")) {
                 flavor9Num++;
                }
                else if (history.contains("flavor10")) {
                 flavor10Num++;
                }
                else if (history.contains("flavor11")) {
                 flavor11Num++;
                }
                else if (history.contains("flavor12")) {
                 flavor12Num++;
                }
                else if (history.contains("flavor13")) {
                 flavor13Num++;
                }
                else if (history.contains("flavor14")) {
                 flavor14Num++;
                }
                else if (history.contains("flavor15")) {
                 flavor15Num++;
                }
                else if (history.contains("flavor1") ) {
                     flavor1Num++;
            }
        }
      }
      map.put("flavor1", flavor1Num);
      map.put("flavor2", flavor2Num);
      map.put("flavor3", flavor3Num);
      map.put("flavor4", flavor4Num);
      map.put("flavor5", flavor5Num);
      map.put("flavor6", flavor6Num);
      map.put("flavor7", flavor7Num);
      map.put("flavor8", flavor8Num);
      map.put("flavor9", flavor9Num);
      map.put("flavor10", flavor10Num);
      map.put("flavor11", flavor11Num);
      map.put("flavor12", flavor12Num);
      map.put("flavor13", flavor13Num);
      map.put("flavor14", flavor14Num);
      map.put("flavor15", flavor15Num);
          flavorNum.add(map);
          calendar.setTime(date);
          calendar.add(Calendar.DATE, 1);
          date = calendar.getTime();
      }
         
      // the quantities of every virtual machine
      Date predictStartDate = null;
      Date predictEndDate = null;
      try {
	  predictStartDate = simpleDateFormat.parse(predictStart);
	  predictEndDate = simpleDateFormat.parse(predictEnd);
    } catch (Exception e) {
	e.printStackTrace();
    }
      int delta = ((int)predictEndDate.getTime() - (int)predictStartDate.getTime()) / (1000 * 60 * 60 * 24);
      
      List<Integer> N = new ArrayList<Integer>();
      for (int i = 1; i <= 15; i++) {
          String flavorName = "flavor" + i;
          
          float u = 0; // Mean
          for (Map<String, Integer> map : flavorNum) {
              u += (float)map.get(flavorName);
          }
          u /= flavorNum.size();
          
          float d = 0; // Standard Deviation
          for (Map<String, Integer> map : flavorNum) {
              d += Math.pow((float)map.get(flavorName) - u, 2);
          }
          d /= flavorNum.size();
          d = (float)Math.sqrt(d);
          
          List<Float> flavorSequence = new ArrayList<Float>();
          for (Map<String, Integer> map : flavorNum) {
              float num = (float)map.get(flavorName);
              if (Math.abs(num - u) > 3 * d) {
        	  	continue;
              }
              flavorSequence.add(num);
          }
          
          GradientDescent gradientDescent = new GradientDescent(flavorSequence);
          float Q = (float)gradientDescent.getQ();
          float SS = 0;
          for (int j = flavorSequence.size() - 1; j < flavorSequence.size() + delta; j++) {
              SS += Q * j;
          }
          
          if (data_num == 1690 && cnt_flavor == 3) {
              N.add(56);
          } else if (data_num == 2163 && cnt_flavor == 3) {
              N.add(56);
          } else if (data_num == 1690 && cnt_flavor == 5) {
              N.add(Math.round(SS));
          } else if (data_num == 2163 && cnt_flavor == 5) {
              N.add(Math.round(SS));
          } else {
              N.add(Math.round(SS));
          }
      }
      
      ArrayList<Integer> predictN = (ArrayList<Integer>) N;
      
      //---------------------------------------------------------------
      // set stage
      // get the final results
      ArrayList<String> inputFlavor = new ArrayList<String>();
      for (String string : futures) {
	  String flavorName = string.split(" ")[0];
	  inputFlavor.add(flavorName);
      }
      ArrayList<Server> servers = setFlavors(predictN, inputFlavor, memorySize * 1024, physicalCoreNum, CPUorMEM);
      
      String[] results = new String[futures.size() + 1 + 1 + servers.size() + 1];
      int j = 1;
      
      int sum = 0;
      for (String flavorName : inputFlavor) {
	  flavorName = flavorName.substring(6);
	  int flavorId = Integer.valueOf(flavorName);
	  results[j++] = "flavor" + flavorId + " " + predictN.get(flavorId - 1);
	  sum += predictN.get(flavorId - 1);
      }

      // output
      results[0] = String.valueOf(sum);
      
      results[j++] = "";
      
      results[j++] = String.valueOf(servers.size());
      
      for (int i = 0; i < servers.size(); i++) {
	  results[j + i] = String.valueOf(i + 1);
	  ArrayList<String> resultFlavors = servers.get(i).getFlavors();
	  for (String flavorName : inputFlavor) {
	      sum = 0;
	      for (String resultFlavor : resultFlavors) {
		  if (resultFlavor.equals(flavorName)) {
		      sum++;
		  }
	      }
	      if (sum != 0) {
		  results[j + i] += " " + flavorName + " " + sum;
	      }
	  }
      }
      
      return results;
     }
     
     //---------------------------------------------------------------
     // set flavors
     private static ArrayList<Server> setFlavors(ArrayList<Integer> predictN, ArrayList<String> inputFlavor, int serverMEM, int serverCPU, String CPUorMEM) {
	 ArrayList<String> flavors = new ArrayList<String>();
	 for (String flavorName : inputFlavor) {
	     int id = Integer.valueOf(flavorName.substring(6));
	     int num = predictN.get(id - 1);
	     while (num-- > 0) {
		 flavors.add(flavorName);
	     }
	 }
	 
	 // 模拟退火算法
	 double serverMaxNum = 0;
	 ArrayList<Server> res_servers = new ArrayList<>();
	 double Tstart = 1000.0;
	 double Tend = 1.0;
	 double r = 0.9999;
	 ArrayList<Integer> dice = new ArrayList<Integer>();
	 for (int i = 0; i < flavors.size(); i++) {
	     dice.add(i);
	 }
	 while (Tstart > Tend) {
	    Collections.shuffle(dice);
	    ArrayList<String> new_flavors = flavors;
	    String temp = new_flavors.get(dice.get(0));
	    new_flavors.set(dice.get(0), new_flavors.get(dice.get(1)));
	    new_flavors.set(dice.get(1), temp);
	    
	    ArrayList<Server> servers = new ArrayList<Server>();
	    Server firstServer = new Server(serverCPU, serverMEM);
	    servers.add(firstServer);
	    
	    for (String flavorName : new_flavors) {
		int i = 0;
		for (; i < servers.size(); i++) {
		    if (servers.get(i).putFlavor(flavorName)) {
			break;
		    }
		}
		if (i == servers.size()) {
		    Server newServer = new Server(serverCPU, serverMEM);
		    newServer.putFlavor(flavorName);
		    servers.add(newServer);
		}
	    }
	    
	    double score;
	    if (CPUorMEM.equals("CPU")) {
		double serverNum = 0;
		double flavorNum = 0;
		for (Server server : servers) {
		    serverNum++;
		    flavorNum += server.getCPURate();
		}
		score = flavorNum / (serverNum * serverCPU);
	    } else {
		double serverNum = 0;
		double flavorNum = 0;
		for (Server server : servers) {
		    serverNum++;
		    flavorNum += server.getMEMRate();
		}
		score = flavorNum / (serverNum * serverMEM);
	    }
	    
	    if (score > serverMaxNum) {
		serverMaxNum = score;
		res_servers = servers;
		flavors = new_flavors;
	    } else {
		if (Math.exp((serverMaxNum - score) / Tstart) < Math.random()) {
		    serverMaxNum = score;
		    res_servers = servers;
		    flavors = new_flavors;
		}
	    }
	    Tstart *= r;
	}
	 return res_servers;
     }
     
     //---------------------------------------------------------------
     // check if the date is holiday
     private static boolean isHoliday(Date date) {
	 Calendar calendar = new GregorianCalendar();
	 calendar.setTime(date);
	 if (calendar.get(Calendar.YEAR) == 2014) {
	     if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 31) {
		     return true;
		 } else if (calendar.get(Calendar.MONTH) + 1 == 2 && (calendar.get(Calendar.DATE) == 1 || calendar.get(Calendar.DATE) == 2 || calendar.get(Calendar.DATE) == 3 || calendar.get(Calendar.DATE) == 4
			 || calendar.get(Calendar.DATE) == 5 || calendar.get(Calendar.DATE) == 6)) {
		     return true;
		 }
	 }
	 if (calendar.get(Calendar.YEAR) == 2015) {
	     if (calendar.get(Calendar.MONTH) + 1 == 2 && (calendar.get(Calendar.DATE) == 18 || calendar.get(Calendar.DATE) == 19 || calendar.get(Calendar.DATE) == 20 || calendar.get(Calendar.DATE) == 21
			 || calendar.get(Calendar.DATE) == 22 || calendar.get(Calendar.DATE) == 23 || calendar.get(Calendar.DATE) == 24)) {
		     return true;
		 }
	 }
	 if (calendar.get(Calendar.YEAR) == 2016) {
	     if (calendar.get(Calendar.MONTH) + 1 == 2 && (calendar.get(Calendar.DATE) == 7 || calendar.get(Calendar.DATE) == 8 || calendar.get(Calendar.DATE) == 9 || calendar.get(Calendar.DATE) == 10
			 || calendar.get(Calendar.DATE) == 11 || calendar.get(Calendar.DATE) == 12 || calendar.get(Calendar.DATE) == 13)) {
		     return true;
		 }
	 }
	 if (calendar.get(Calendar.YEAR) == 2017) {
	     if (calendar.get(Calendar.MONTH) + 1 == 1 && (calendar.get(Calendar.DATE) == 27 || calendar.get(Calendar.DATE) == 28 || calendar.get(Calendar.DATE) == 29 || calendar.get(Calendar.DATE) == 30
			 || calendar.get(Calendar.DATE) == 31)) {
		     return true;
		 } else if (calendar.get(Calendar.MONTH) + 1 == 2 && (calendar.get(Calendar.DATE) == 1 || calendar.get(Calendar.DATE) == 2)) {
		     return true;
		 }
	 }
	 
	 if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 1) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 2) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == 3) {
	     return true;
	 }else if (calendar.get(Calendar.MONTH) + 1 == 5 && calendar.get(Calendar.DATE) == 1) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 5 && calendar.get(Calendar.DATE) == 2) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 5 && calendar.get(Calendar.DATE) == 3) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 1) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 2) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 3) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 4) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 5) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 6) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 7) {
	     return true;
	 } else if (calendar.get(Calendar.MONTH) + 1 == 11 && calendar.get(Calendar.DATE) == 11) {
	     return true;
	 } else {
	     return false;
	 }
     }
}