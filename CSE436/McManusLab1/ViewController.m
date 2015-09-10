//
//  ViewController.m
//  McManusLab1
//
//  Created by Sean McManus on 9/1/15.
//  Copyright (c) 2015 Sean McManus. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

@synthesize wuPic;
@synthesize wuSwitch;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    wuPic.hidden = YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)switchChanged:(id)sender {
    if(wuSwitch.on){
        wuPic.hidden = NO;
    }
    else{
        wuPic.hidden = YES;
    }
}

@end
