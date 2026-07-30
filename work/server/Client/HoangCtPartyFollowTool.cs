using System;

public class HoangCtPartyFollowTool
{
	private const string LEADER_NAME = "hoangct1";

	private const string COMMAND_PREFIX = "/hct";

	private const long INVITE_DELAY = 2500L;

	private const long FOLLOW_DELAY = 500L;

	private const int FOLLOW_DISTANCE = 56;

	private const int FOLLOW_STOP_DISTANCE = 28;

	private static readonly string[] MEMBER_NAMES = new string[5] { "hoangct1", "hoangct2", "hoangct3", "hoangct4", "hoangct5" };

	private static readonly int[] FOLLOW_OFFSET_X = new int[5] { 0, -24, 24, 0, 0 };

	private static readonly int[] FOLLOW_OFFSET_Y = new int[5] { 0, 0, 0, -24, 24 };

	private static bool isEnabled;

	private static bool isLoaded;

	private static bool isStatusShown;

	private static string currentCharName = string.Empty;

	private static long nextInviteTime;

	private static long nextFollowTime;

	public static void update(GameScr gameScr)
	{
		if (gameScr == null || gameScr.mainChar == null || gameScr.mainChar.name == null)
		{
			return;
		}
		string text = gameScr.mainChar.name.Trim().ToLower();
		if (text.Length == 0)
		{
			return;
		}
		loadState(text);
		if (!isEnabled || !isManagedAccount(text))
		{
			return;
		}
		if (!isStatusShown)
		{
			showMessage(gameScr, "hct tool dang bat");
			isStatusShown = true;
		}
		if (isLeader(text))
		{
			updateLeader(gameScr, text);
		}
		else
		{
			updateFollower(gameScr, text);
		}
	}

	public static bool handleCommand(GameScr gameScr, string text)
	{
		if (gameScr == null || text == null)
		{
			return false;
		}
		string text2 = text.Trim();
		if (text2.Length == 0)
		{
			return false;
		}
		string text3 = text2.ToLower();
		if (!text3.StartsWith(COMMAND_PREFIX) && !text3.Equals("hct") && !text3.StartsWith("hct "))
		{
			return false;
		}
		if (gameScr.mainChar == null || gameScr.mainChar.name == null)
		{
			showMessage(gameScr, "hct tool chua san sang");
			return true;
		}
		string text4 = gameScr.mainChar.name.Trim().ToLower();
		loadState(text4);
		string text5 = getArgument(text3);
		if (text5.Equals("help"))
		{
			showMessage(gameScr, "lenh: /hct on, /hct off, /hct status");
			return true;
		}
		if (text5.Equals("status"))
		{
			if (!isManagedAccount(text4))
			{
				showMessage(gameScr, "tool chi dung cho hoangct1 den hoangct5");
			}
			else
			{
				showMessage(gameScr, "hct tool: " + (isEnabled ? "bat" : "tat") + " - vai tro: " + (isLeader(text4) ? "leader" : "follower"));
			}
			return true;
		}
		if (!isManagedAccount(text4))
		{
			showMessage(gameScr, "tool chi dung cho hoangct1 den hoangct5");
			return true;
		}
		bool flag = isEnabled;
		if (text5.Equals("on"))
		{
			flag = true;
		}
		else if (text5.Equals("off"))
		{
			flag = false;
		}
		else
		{
			flag = !isEnabled;
		}
		setEnabled(gameScr, text4, flag);
		showMessage(gameScr, "hct tool da " + (isEnabled ? "bat" : "tat"));
		return true;
	}

	public static bool shouldAutoAcceptPartyInvite(string mainCharName, string inviteName)
	{
		if (mainCharName == null || inviteName == null)
		{
			return false;
		}
		string text = mainCharName.Trim().ToLower();
		string text2 = inviteName.Trim().ToLower();
		loadState(text);
		if (!isEnabled || !isFollower(text))
		{
			return false;
		}
		return text2.Equals(LEADER_NAME);
	}

	public static bool isManagedAccount(GameScr gameScr)
	{
		string currentName = getCurrentName(gameScr);
		if (currentName.Length == 0)
		{
			return false;
		}
		loadState(currentName);
		return isManagedAccount(currentName);
	}

	public static bool isToolEnabled(GameScr gameScr)
	{
		string currentName = getCurrentName(gameScr);
		if (currentName.Length == 0)
		{
			return false;
		}
		loadState(currentName);
		return isEnabled;
	}

	public static void toggleFromUi(GameScr gameScr)
	{
		string currentName = getCurrentName(gameScr);
		if (currentName.Length == 0)
		{
			showMessage(gameScr, "hct tool chua san sang");
			return;
		}
		loadState(currentName);
		if (!isManagedAccount(currentName))
		{
			showMessage(gameScr, "tool chi dung cho hoangct1 den hoangct5");
			return;
		}
		setEnabled(gameScr, currentName, !isEnabled);
		showMessage(gameScr, "hct tool da " + (isEnabled ? "bat" : "tat"));
	}

	public static string getUiStatus(GameScr gameScr)
	{
		string currentName = getCurrentName(gameScr);
		if (currentName.Length == 0)
		{
			return "tool chua san sang";
		}
		loadState(currentName);
		if (!isManagedAccount(currentName))
		{
			return "chi ho tro hoangct1 - hoangct5";
		}
		return "trang thai: " + (isEnabled ? "bat" : "tat") + " - vai tro: " + getRoleName(currentName);
	}

	private static void updateLeader(GameScr gameScr, string charName)
	{
		long currentTimeMillis = mSystem.currentTimeMillis();
		if (currentTimeMillis < nextInviteTime || gameScr.mainChar.isTrade || gameScr.mainChar.state == 3)
		{
			return;
		}
		if (gameScr.mainChar.IDParty == -1)
		{
			if (findVisibleFollower(gameScr) != null)
			{
				gameScr.gameService.requestCreateParty(gameScr.mainChar.ID);
				nextInviteTime = currentTimeMillis + INVITE_DELAY;
			}
			return;
		}
		if (gameScr.mainChar.IDMasterParty != gameScr.mainChar.ID || Char.party.size() >= 4)
		{
			return;
		}
		for (int i = 1; i < MEMBER_NAMES.Length; i++)
		{
			if (isInParty(charName, MEMBER_NAMES[i]))
			{
				continue;
			}
			Char @char = findVisibleCharByName(gameScr, MEMBER_NAMES[i]);
			if (@char != null)
			{
				gameScr.gameService.invite2Party(@char.ID);
				showMessage(gameScr, "dang moi " + MEMBER_NAMES[i] + " vao nhom");
				nextInviteTime = currentTimeMillis + INVITE_DELAY;
				return;
			}
		}
	}

	private static void updateFollower(GameScr gameScr, string charName)
	{
		if (Canvas.currentDialog != null || gameScr.chatMode || gameScr.mainChar.isTrade || gameScr.mainChar.state == 3 || Tilemap.isOfflineMap)
		{
			return;
		}
		long currentTimeMillis = mSystem.currentTimeMillis();
		if (currentTimeMillis < nextFollowTime)
		{
			return;
		}
		Char @char = findVisibleCharByName(gameScr, LEADER_NAME);
		if (@char == null)
		{
			return;
		}
		int indexMember = getMemberIndex(charName);
		if (indexMember < 1)
		{
			return;
		}
		int num = @char.x + FOLLOW_OFFSET_X[indexMember];
		int num2 = @char.y + FOLLOW_OFFSET_Y[indexMember];
		num = clamp(num, 16, Tilemap.pxw - 16);
		num2 = clamp(num2, 16, Tilemap.pxh - 16);
		int distance = Util.distance(gameScr.mainChar.x, gameScr.mainChar.y, num, num2);
		if (distance <= FOLLOW_STOP_DISTANCE)
		{
			nextFollowTime = currentTimeMillis + FOLLOW_DELAY;
			return;
		}
		if (distance >= FOLLOW_DISTANCE || gameScr.mainChar.posTransRoad == null)
		{
			gameScr.findRoad2(num, num2);
		}
		nextFollowTime = currentTimeMillis + FOLLOW_DELAY;
	}

	private static void setEnabled(GameScr gameScr, string charName, bool value)
	{
		isEnabled = value;
		saveState(charName, value);
		if (!value && gameScr != null && gameScr.mainChar != null)
		{
			gameScr.mainChar.posTransRoad = null;
			gameScr.mainChar.countRoad = 0;
		}
	}

	private static void loadState(string charName)
	{
		if (isLoaded && currentCharName.Equals(charName))
		{
			return;
		}
		currentCharName = charName;
		isEnabled = false;
		isLoaded = true;
		isStatusShown = false;
		nextInviteTime = 0L;
		nextFollowTime = 0L;
		sbyte[] array = RMS.loadRMS(getSaveKey(charName));
		if (array == null)
		{
			return;
		}
		try
		{
			DataInputStream dataInputStream = new DataInputStream(array);
			isEnabled = dataInputStream.readBoolean();
			dataInputStream.close();
		}
		catch (Exception)
		{
			isEnabled = false;
		}
	}

	private static void saveState(string charName, bool value)
	{
		DataOutputStream dataOutputStream = new DataOutputStream();
		try
		{
			dataOutputStream.writeBoolean(value);
			RMS.saveRMS(getSaveKey(charName), dataOutputStream.toByteArray());
			dataOutputStream.close();
		}
		catch (Exception)
		{
		}
	}

	private static string getSaveKey(string charName)
	{
		return "hct_party_follow_" + charName;
	}

	private static Char findVisibleFollower(GameScr gameScr)
	{
		for (int i = 1; i < MEMBER_NAMES.Length; i++)
		{
			Char @char = findVisibleCharByName(gameScr, MEMBER_NAMES[i]);
			if (@char != null)
			{
				return @char;
			}
		}
		return null;
	}

	private static Char findVisibleCharByName(GameScr gameScr, string charName)
	{
		if (gameScr == null || charName == null)
		{
			return null;
		}
		for (int i = 0; i < gameScr.actors.size(); i++)
		{
			Actor actor = (Actor)gameScr.actors.elementAt(i);
			if (actor != null && actor.catagory == Actor.CAT_PLAYER && actor is Char && actor.ID != gameScr.mainChar.ID && equalsName(((Char)actor).name, charName))
			{
				return (Char)actor;
			}
		}
		return null;
	}

	private static bool isInParty(string currentName, string memberName)
	{
		if (equalsName(currentName, memberName))
		{
			return true;
		}
		for (int i = 0; i < Char.party.size(); i++)
		{
			PartyInfo partyInfo = (PartyInfo)Char.party.elementAt(i);
			if (partyInfo != null && equalsName(partyInfo.name, memberName))
			{
				return true;
			}
		}
		return false;
	}

	private static int getMemberIndex(string charName)
	{
		for (int i = 0; i < MEMBER_NAMES.Length; i++)
		{
			if (equalsName(MEMBER_NAMES[i], charName))
			{
				return i;
			}
		}
		return -1;
	}

	private static bool isManagedAccount(string charName)
	{
		return getMemberIndex(charName) != -1;
	}

	private static bool isLeader(string charName)
	{
		return equalsName(charName, LEADER_NAME);
	}

	private static bool isFollower(string charName)
	{
		int memberIndex = getMemberIndex(charName);
		return memberIndex > 0;
	}

	private static bool equalsName(string first, string second)
	{
		if (first == null || second == null)
		{
			return false;
		}
		return first.Trim().ToLower().Equals(second.Trim().ToLower());
	}

	private static int clamp(int value, int min, int max)
	{
		if (value < min)
		{
			return min;
		}
		if (value > max)
		{
			return max;
		}
		return value;
	}

	private static void showMessage(GameScr gameScr, string message)
	{
		if (gameScr != null)
		{
			gameScr.addChat(new ChatInfo(string.Empty, message, 0));
		}
	}

	private static string getArgument(string text)
	{
		int num = text.IndexOf(' ');
		if (text.Equals("hct") || num < 0 || num >= text.Length - 1)
		{
			return string.Empty;
		}
		return text.Substring(num + 1).Trim();
	}

	private static string getRoleName(string charName)
	{
		if (!isManagedAccount(charName))
		{
			return "ngoai nhom";
		}
		if (isLeader(charName))
		{
			return "leader";
		}
		return "follower";
	}

	private static string getCurrentName(GameScr gameScr)
	{
		if (gameScr == null || gameScr.mainChar == null || gameScr.mainChar.name == null)
		{
			return string.Empty;
		}
		return gameScr.mainChar.name.Trim().ToLower();
	}
}
