"use client";

import { Button } from "../ui/button";
import {
  renderThumb,
  renderTrack,
  renderView,
} from "@/components/scrollbar/Scrollbar";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Card } from "@/components/ui/card";

import { MouseEvent as MouseEventReact, PropsWithChildren } from "react";
import { Scrollbars } from "react-custom-scrollbars-2";
import { HiX } from "react-icons/hi";
import { HiBolt } from "react-icons/hi2";
import { HiOutlineArrowRightOnRectangle } from "react-icons/hi2";
import { IRoute } from "@/types/types";
import { router } from "@/main";
import MyLink from "./components/MyLink";

export interface SidebarProps extends PropsWithChildren {
  [x: string]: any;
}

const routes: IRoute[] = [
  {
    name: "Trang chủ",
    path: "/",
  },
  {
    name: "Thí sinh",
    path: "/contestants",
  },
  {
    name: "Điểm số",
    path: "/scores",
  },
];

function Sidebar(props: SidebarProps) {
  const handleSignOut = async (
    e: MouseEventReact<HTMLButtonElement, MouseEvent>,
  ) => {
    e.preventDefault();
    router.navigate({ to: "/" });
  };

  // SIDEBAR
  return (
    <div
      className={`lg:!z-99 fixed !z-[99] min-h-full w-[300px] transition-all md:!z-[99] xl:!z-0 ${
        props.variant === "auth" ? "xl:hidden" : "xl:block"
      } ${props.open ? "" : "-translate-x-[120%] xl:translate-x-[unset]"}`}
    >
      <Card
        className={`m-3 ml-3 h-[96.5vh] w-full overflow-hidden !rounded-lg border-zinc-200 pe-4 dark:border-zinc-800 sm:my-4 sm:mr-4 md:m-5 md:mr-[-50px]`}
      >
        <Scrollbars
          autoHide
          renderTrackVertical={renderTrack}
          renderThumbVertical={renderThumb}
          renderView={renderView}
        >
          <div className="flex h-full flex-col justify-between">
            <div>
              <span
                className="absolute top-4 block cursor-pointer text-zinc-200 dark:text-white/40 xl:hidden"
                onClick={() => props.setOpen(false)}
              >
                <HiX />
              </span>
              <div className={`mt-8 flex items-center justify-center`}>
                <div className="me-2 flex h-[40px] w-[40px] items-center justify-center rounded-md bg-primary text-white dark:bg-white dark:text-zinc-950">
                  <HiBolt className="h-5 w-5" />
                </div>
                <h5 className="me-2 text-2xl font-bold leading-6 text-primary dark:text-white">
                  VSTEP Chatbot
                </h5>
              </div>
              <div className="mb-8 mt-8 h-px bg-zinc-200 dark:bg-white/10" />
              {/* Nav item */}
              <ul>
                {routes.map((route) => (
                  <MyLink key={route.path} route={route} />
                ))}
              </ul>
            </div>
            {/* Free Horizon Card    */}
            <div className="mb-9 mt-7">
              {/* Sidebar profile info */}
              <div className="mt-5 flex w-full items-center rounded-lg border border-zinc-200 p-4 dark:border-zinc-800">
                <a href="/dashboard/dashboard/settings">
                  <Avatar className="min-h-10 min-w-10">
                    <AvatarImage src="https://avatar.iran.liara.run/public" />
                    <AvatarFallback className="font-bold dark:text-zinc-950">
                      {/* {userDetails.full_name
                        ? `${userDetails.full_name[0]}`
                        : `${user?.user_metadata.email[0].toUpperCase()}`} */}
                    </AvatarFallback>
                  </Avatar>
                </a>
                <a href="/dashboard/settings">
                  <p className="ml-2 mr-3 flex items-center text-sm font-semibold leading-none text-zinc-950 dark:text-white">
                    {
                      // userDetails?.full_name ||
                      //   user?.user_metadata?.full_name ||
                      "SGU Admin"
                    }
                  </p>
                </a>
                <Button
                  onClick={handleSignOut}
                  variant="outline"
                  className="ml-auto flex h-[40px] w-[40px] cursor-pointer items-center justify-center rounded-full p-0 text-center text-sm font-medium hover:dark:text-white"
                  type="submit"
                >
                  <HiOutlineArrowRightOnRectangle
                    className="h-4 w-4 stroke-2 text-zinc-950 dark:text-white"
                    width="16px"
                    height="16px"
                    color="inherit"
                  />
                </Button>
              </div>
            </div>
          </div>
        </Scrollbars>
      </Card>
    </div>
  );
}

// PROPS

export default Sidebar;
