����   4 �  io/github/stonley890/App  !org/bukkit/plugin/java/JavaPlugin  org/bukkit/event/Listener token Ljava/lang/String; plugin Lio/github/stonley890/App; <init> ()V Code
      FOTQyMzQyMjMwOTY0ODU4OTEw.GLt3zv.0nCFq9qeXqFP7NPeURMtNr9y68bRaWJdfmcH2I	     LineNumberTable LocalVariableTable this onEnable
     	getLogger ()Ljava/util/logging/Logger;  LDreamvisitor: A plugin created by Bog for WoF:TNW to add Discord automation.
  !   java/util/logging/Logger " # info (Ljava/lang/String;)V
  % & ' 	getServer ()Lorg/bukkit/Server; ) + * org/bukkit/Server , - getPluginManager #()Lorg/bukkit/plugin/PluginManager; / 1 0 org/bukkit/plugin/PluginManager 2 3 registerEvents 8(Lorg/bukkit/event/Listener;Lorg/bukkit/plugin/Plugin;)V 5 io/github/stonley890/Bot
 4 	 8 : 9 java/lang/System ; < out Ljava/io/PrintStream; > ERROR: Bot login failed!
 @ B A java/io/PrintStream C # println
 E G F (javax/security/auth/login/LoginException H  printStackTrace
 4 J K L getJDA ()Lnet/dv8tion/jda/api/JDA; N P O net/dv8tion/jda/api/JDA Q L 
awaitReady
 S G T java/lang/InterruptedException N V W X 	getGuilds ()Ljava/util/List;   Z [ \ accept ()Ljava/util/function/Consumer; ^ ` _ java/util/List a b forEach  (Ljava/util/function/Consumer;)V	  d 	 
 e *Ljavax/security/auth/login/LoginException;  Ljava/lang/InterruptedException; StackMapTable 	getPlugin ()Lio/github/stonley890/App; onPlayerChatEvent 1(Lorg/bukkit/event/player/AsyncPlayerChatEvent;)V RuntimeVisibleAnnotations Lorg/bukkit/event/EventHandler; p java/lang/StringBuilder r **
 o t  #
 v x w ,org/bukkit/event/player/AsyncPlayerChatEvent y z 	getPlayer ()Lorg/bukkit/entity/Player; | ~ } org/bukkit/entity/Player  � getName ()Ljava/lang/String;
 o � � � append -(Ljava/lang/String;)Ljava/lang/StringBuilder; � **: 
 v � � � 
getMessage
 o � � � toString
 � � � -io/github/stonley890/commands/CommandsManager � � getChatChannel � none N � � � getTextChannelById >(Ljava/lang/String;)Lnet/dv8tion/jda/api/entities/TextChannel; � � � (net/dv8tion/jda/api/entities/TextChannel � � sendMessage Q(Ljava/lang/CharSequence;)Lnet/dv8tion/jda/api/requests/restaction/MessageAction; � � � 5net/dv8tion/jda/api/requests/restaction/MessageAction �  queue event .Lorg/bukkit/event/player/AsyncPlayerChatEvent; chatMessage 	channelId � java/lang/String onPlayerJoinEvent ,(Lorg/bukkit/event/player/PlayerJoinEvent;)V
 � x � 'org/bukkit/event/player/PlayerJoinEvent �  joined the game** )Lorg/bukkit/event/player/PlayerJoinEvent; onPlayerQuitEvent ,(Lorg/bukkit/event/player/PlayerQuitEvent;)V
 � x � 'org/bukkit/event/player/PlayerQuitEvent �  left the game** )Lorg/bukkit/event/player/PlayerQuitEvent; onPlayerDeathEvent -(Lorg/bukkit/event/entity/PlayerDeathEvent;)V
 � � � (org/bukkit/event/entity/PlayerDeathEvent � � getDeathMessage *Lorg/bukkit/event/entity/PlayerDeathEvent; 	onDisable � Dreamvisitor disable process.  Z N � �  shutdown lambda$0 '(Lnet/dv8tion/jda/api/entities/Guild;)V � � � "net/dv8tion/jda/api/entities/Guild � � getSystemChannel ,()Lnet/dv8tion/jda/api/entities/TextChannel; � Server has been started. guild $Lnet/dv8tion/jda/api/entities/Guild; lambda$1 � Server has been shutdown. 
SourceFile App.java BootstrapMethods
 � � � "java/lang/invoke/LambdaMetafactory � � metafactory �(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; � (Ljava/lang/Object;)V �
  � � � � � �
  � � � � � InnerClasses � %java/lang/invoke/MethodHandles$Lookup � java/lang/invoke/MethodHandles Lookup !            
 	 
   
        =     *� *� �           !  # 
 !          
          �     W*� � *� $� ( **� . � 4� 6� L� 7=� ?+� D� I� M W� L+� R� I� U � Y  � ] *� c�    " E / 8 ; S     6    )  * 	 +  .  / # 0 + 1 / 4 8 5 < 6 @ 8 R : V <         W  
   #  e f  <  e g  h    b EK S 	 i j     $      � c�           ?        k l  m     n      �     D� oYq� s+� u� { � ��� �+� �� �� �M� �N-�� � I-� � ,� � � � �           Z % [ ) \ / ] C a    *    D  
     D � �  %  �   )  �   h    � C � �  � �  m     n      �     =� oYq� s+� �� { � ��� �� �M� �N-�� � I-� � ,� � � � �           f  g " h ( i < k    *    =  
     = � �    �   "  �   h    � < � �  � �  m     n      �     =� oYq� s+� �� { � ��� �� �M� �N-�� � I-� � ,� � � � �           p  q " r ( s < u    *    =  
     = � �    �   "  �   h    � < � �  � �  m     n      �     8� oYq� s+� �� �q� �� �M� �N-�� � I-� � ,� � � � �           z  {  | # } 7     *    8  
     8 � �    �     �   h    � 7 � �  �      Z     $*� ¶ � I� U � �  � ] � I� � �           � 	 �  � # �        $  
  
 � �     =     *� � й � � � �           8         � �  
 � �     =     *� � չ � � � �           �         � �    �    � �     �  � � � �  � � � �   
  � � � 