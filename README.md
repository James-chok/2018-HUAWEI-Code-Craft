# 2018华为软件精英挑战赛
## 题目：
根据用户对虚拟机请求的历史数据，对未来n天用户需要的虚拟机数量和种类进行预测，然后根据预测结果，考虑应该部署多少台物理服务器。  
### 个人成绩和解题思路：
初赛最终成绩是236.859/300，粤港澳赛区第33名。  
预测阶段使用的算法是通过梯度下降法计算线性回归方程，并利用该方程预测未来n天所需要的虚拟机数量。  
放置阶段使用的算法是结合模拟退火算法和首次适配算法来将虚拟机放到尽可能少的物理机上。
