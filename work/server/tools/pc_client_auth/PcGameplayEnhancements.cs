using System;

namespace KpahPcGameplay
{
    public static class InputEnhancements
    {
        public static bool HandleEnter(int keyCode)
        {
            if (keyCode == 85 || keyCode == 117)
            {
                return OpenPlayerSupportMenu();
            }
            if (keyCode != -5 && keyCode != 10)
            {
                return false;
            }

            // Hộp thoại thông báo cũ chỉ gắn nút OK ở vị trí phím trái.
            // Cho phép Enter xác nhận khi hộp thoại không có lệnh ở giữa.
            if (Canvas.currentDialog != null)
            {
                if (Canvas.currentDialog.center == null && Canvas.currentDialog.left != null)
                {
                    Canvas.currentDialog.left.perform();
                    return true;
                }
                return false;
            }

            if (Canvas.menu != null && Canvas.menu.showMenu)
            {
                return false;
            }

            WindowInfoScr inventory = Canvas.currentScreen as WindowInfoScr;
            if (inventory == null || WindowInfoScr.index == null ||
                WindowInfoScr.focusTab < 0 || WindowInfoScr.focusTab >= WindowInfoScr.index.Length ||
                WindowInfoScr.index[WindowInfoScr.focusTab] != 0 || inventory.selected < 0)
            {
                return false;
            }

            // Giữ lại lệnh xem thông tin gốc trước khi mở menu thao tác.
            // Mục này giúp Enter lần hai vẫn xem được mô tả và thuộc tính vật phẩm.
            Command viewDetail = inventory.center;
            inventory.doSelectedInventori();
            if (Canvas.menu != null && Canvas.menu.showMenu && Canvas.menu.menuItems != null &&
                viewDetail != null && viewDetail.action != null)
            {
                Command detailCommand = new Command("Xem chi tiết");
                detailCommand.action = delegate
                {
                    Canvas.menu.showMenu = false;
                    viewDetail.perform();
                };
                Canvas.menu.menuItems.insertElementAt(detailCommand, 0);
                Canvas.menu.selected = 0;
            }
            return true;
        }

        private static bool OpenPlayerSupportMenu()
        {
            if (Canvas.currentDialog != null ||
                Canvas.menu == null || Canvas.menu.showMenu ||
                Canvas.gameScr == null || Canvas.gameScr.mainChar == null ||
                Canvas.currentScreen != Canvas.gameScr)
            {
                return false;
            }

            // Gửi lệnh ẩn để server mở menu native; nội dung lệnh không xuất hiện ở khung chat.
            GameService.gI().chat("/kpah-tienich");
            return true;
        }
    }

    public static class PotionUseAll
    {
        private static readonly int[] HpPotionTypes = { 1, 2, 3, 21, 22, 93, 94 };
        private static readonly int[] MpPotionTypes = { 4, 5, 6, 23, 24, 95, 96 };

        private static int activePotionType = -1;
        private static long nextUseAt;
        private static int noProgressCount;

        public static void AddMenuCommand(mVector menu, int potionType)
        {
            if (menu == null || !CanUseAll(potionType))
            {
                return;
            }

            Command useAll = new Command("Dùng tất cả");
            useAll.action = delegate
            {
                Begin(potionType);
            };
            menu.addElement(useAll);
        }

        public static void Update()
        {
            if (activePotionType < 0 || Canvas.gameScr == null || Canvas.gameScr.mainChar == null)
            {
                return;
            }

            MainChar mainChar = Canvas.gameScr.mainChar;
            int potionType = activePotionType;
            if (!HasPotionSlot(mainChar, potionType) || mainChar.potions[potionType] <= 0)
            {
                Stop();
                return;
            }

            bool isHpPotion = Contains(HpPotionTypes, potionType);
            bool isMpPotion = Contains(MpPotionTypes, potionType);
            if ((isHpPotion && mainChar.hp >= mainChar.maxhp) ||
                (isMpPotion && mainChar.mp >= mainChar.maxmp))
            {
                Stop();
                return;
            }

            long now = mSystem.getCurrentTimeMillis();
            if (now < nextUseAt)
            {
                return;
            }

            int beforeCount = mainChar.potions[potionType];
            Canvas.gameScr.doUsePotion(potionType);
            int afterCount = mainChar.potions[potionType];

            if (afterCount >= beforeCount)
            {
                noProgressCount++;
                if (noProgressCount >= 3)
                {
                    Stop();
                    return;
                }
            }
            else
            {
                noProgressCount = 0;
            }

            long configuredDelay = GetConfiguredDelay(potionType);
            long safeDelay = (isHpPotion || isMpPotion)
                ? System.Math.Max(configuredDelay, 100L)
                : System.Math.Max(configuredDelay, 400L);
            nextUseAt = now + safeDelay;

            if (afterCount <= 0)
            {
                Stop();
            }
        }

        private static void Begin(int potionType)
        {
            if (!CanUseAll(potionType))
            {
                return;
            }

            activePotionType = potionType;
            nextUseAt = 0L;
            noProgressCount = 0;
            if (Canvas.menu != null)
            {
                Canvas.menu.showMenu = false;
            }
        }

        private static bool CanUseAll(int potionType)
        {
            MainChar mainChar = Canvas.gameScr == null ? null : Canvas.gameScr.mainChar;
            if (!HasPotionSlot(mainChar, potionType) || mainChar.potions[potionType] <= 1)
            {
                return false;
            }

            // Các loại 10-20 có luồng sử dụng đặc biệt và không trừ số lượng ngay ở client.
            // Không chạy hàng loạt để tránh gửi lặp khi chưa nhận phản hồi từ server.
            return potionType < 10 || potionType > 20;
        }

        private static bool HasPotionSlot(MainChar mainChar, int potionType)
        {
            return mainChar != null && mainChar.potions != null &&
                potionType >= 0 && potionType < mainChar.potions.Length;
        }

        private static long GetConfiguredDelay(int potionType)
        {
            if (MainChar.listPotion == null || potionType < 0 || potionType >= MainChar.listPotion.Length ||
                MainChar.listPotion[potionType] == null)
            {
                return 0;
            }
            return MainChar.listPotion[potionType].delay;
        }

        private static bool Contains(int[] values, int value)
        {
            for (int i = 0; i < values.Length; i++)
            {
                if (values[i] == value)
                {
                    return true;
                }
            }
            return false;
        }

        private static void Stop()
        {
            activePotionType = -1;
            nextUseAt = 0L;
            noProgressCount = 0;
        }
    }
}
