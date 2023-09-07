package matteroverdrive.client.render.conversation;

import matteroverdrive.api.renderer.IDialogShot;

public abstract class DialogShot implements IDialogShot {
    public static DialogShotClose closeUp = new DialogShotClose(1.5f, 0.3f);
    public static DialogShotClose dramaticCloseUp = new DialogShotClose(1.2f, 0.3f);
    public static DialogShotWide wideNormal = new DialogShotWide(0.22f, false, 1);
    public static DialogShotWide wideOpposite = new DialogShotWide(0.22f, true, 1);
    public static DialogShotFromBehind fromBehindLeftClose = new DialogShotFromBehind(2, 4);
    public static DialogShotFromBehind fromBehindLeftFar = new DialogShotFromBehind(3, 4);
    public static DialogShotFromBehind fromBehindRightClose = new DialogShotFromBehind(2, 8);
    public static DialogShotFromBehind fromBehindRightFar = new DialogShotFromBehind(3, -8);
}
