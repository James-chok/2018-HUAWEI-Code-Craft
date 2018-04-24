package com.elasticcloudservice.predict;

import java.util.ArrayList;

public class Server {
    int free_cpu;
    int free_mem;
    int total_cpu;
    int total_mem;
    ArrayList<String> flavors;
    
    // 15 kinds of virtual machines' specification
    int cpuDemand[] = { 1, 1, 1, 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16, 16 };
    int memDemand[] = { 1024, 2048, 4096, 2048, 4096, 8192, 4092, 8192,
      16384, 8192, 16384, 32768, 16384, 32768, 65536 };

    public Server(int cpu, int mem) {
	free_cpu = total_cpu = cpu;
	free_mem = total_mem = mem;
	flavors = new ArrayList<String>();
    }
    
    public ArrayList<String> getFlavors() {
	return flavors;
    }
    
    public boolean putFlavor(String flavorName) {
	int id = Integer.valueOf(flavorName.substring(6));
	int flavor_cpu = cpuDemand[id - 1];
	int flavor_mem = memDemand[id - 1];
	if (free_cpu >= flavor_cpu && free_mem >= flavor_mem) {
	    free_cpu -= flavor_cpu;
	    free_mem -= flavor_mem;
	    flavors.add(flavorName);
	    return true;
	}
	return false;
    }
    
    public int getCPURate() {
	int sum = 0;
	for (String flavorName : flavors) {
	    int id = Integer.valueOf(flavorName.substring(6));
	    sum += cpuDemand[id - 1];
	}
	return sum;
    }
    
    public double getMEMRate() {
	int sum = 0;
	for (String flavorName : flavors) {
	    int id = Integer.valueOf(flavorName.substring(6));
	    sum += memDemand[id - 1];
	}
	return sum;
    }
}
